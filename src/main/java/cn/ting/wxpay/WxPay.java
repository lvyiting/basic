package cn.ting.wxpay;


import com.wechat.pay.java.core.Config;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import com.wechat.pay.java.core.exception.ServiceException;
import com.wechat.pay.java.core.util.PemUtil;
import com.wechat.pay.java.service.transferbatch.TransferBatchService;
import com.wechat.pay.java.service.transferbatch.model.InitiateBatchTransferRequest;
import com.wechat.pay.java.service.transferbatch.model.InitiateBatchTransferResponse;
import com.wechat.pay.java.service.transferbatch.model.TransferDetailInput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class WxPay {

	//商户号
	@Value("${merchant_id}")
	private static String merchantId;
	//商户API证书的证书序列号。 微信商户平台证书的序列号
	@Value("${merchant_serial_number}")
	private static String merchantSerialNumber;
	//微信支付 APIv3 密钥
	@Value("${api_v3_key}")
	private static String apiV3Key;
	//微信支付 APIv3 密钥
	@Value("${apiclient_key}")
	private static String apiclientKey;//SpringCould打包后jar包中获取文件地址会查找不到，所以将秘钥内容配置在application文件中

	//微信支付平台证书列表。你也可以使用后面章节提到的“定时更新平台证书功能”，而不需要关心平台证书的来龙去脉。
//    public static String wechatPayCertificatePath = "classpath:certificate/apiclient_cert.pem";
	//商户API私钥，如何加载商户API私钥请看常见问题。
//    public static String privateKeyPath = "classpath:certificate/apiclient_key.pem";//"商户平台证书密钥的路径";

//    public static String wechatPayCertificatesFromPath = "classpath:certificate/wechatpay_xxxxxxxxxxxxxxxxxxxxx.pem";


	public static void main(String[] args) {
		//通过获取相对路径获取apiclient_key.pem 文件，但是打包后文件在jar包中，获取不到路径
//        ClassPathResource resourcePrivateKey = new ClassPathResource("apiclient_key.pem");
//        String resourcePrivateKeyPath = null;
//        try {
//            resourcePrivateKeyPath = resourcePrivateKey.getFile().getPath();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
		// 初始化商户配置
		Config config =
				new RSAAutoCertificateConfig.Builder()
						.merchantId(merchantId)
						//privateKey 或者 privateKeyFromPath 配置一个就可以了
						.privateKey(getPrivateKey(apiclientKey))//   .privateKeyFromPath(resourcePrivateKeyPath)
						.merchantSerialNumber(merchantSerialNumber)
//                        .wechatPayCertificatesFromPath(wechatPayCertificatesFromPath)//使用RSAAutoCertificateConfig后不再需要配置
						.apiV3Key(apiV3Key)
						.build();
		TransferBatchService  service = new TransferBatchService.Builder().config(config).build();
		//数据封装
		InitiateBatchTransferRequest initiateBatchTransferRequest = new InitiateBatchTransferRequest();
		initiateBatchTransferRequest.setAppid("商户appid");
		initiateBatchTransferRequest.setOutBatchNo(System.currentTimeMillis()+"");
		initiateBatchTransferRequest.setBatchName("XXX申请提现");
		initiateBatchTransferRequest.setBatchRemark("XXX申请提现");
		initiateBatchTransferRequest.setTotalAmount((long) (1*100));
		initiateBatchTransferRequest.setTotalNum(1);
		initiateBatchTransferRequest.setTransferSceneId("1001");
		{
			List<TransferDetailInput> transferDetailListList = new ArrayList<>();
			{
				TransferDetailInput transferDetailInput = new TransferDetailInput();
				transferDetailInput.setTransferAmount((long) (1*100));//金额为分 需要乘以100
				transferDetailInput.setOutDetailNo("D"+System.currentTimeMillis()+"");
				transferDetailInput.setOpenid("收款人Openid");
				transferDetailInput.setTransferRemark("XXX申请提现");
				transferDetailListList.add(transferDetailInput);
			}
			initiateBatchTransferRequest.setTransferDetailList(
					transferDetailListList);
		}
		//发起商家转账
		InitiateBatchTransferResponse response = new InitiateBatchTransferResponse();
		try {
			response = service.initiateBatchTransfer(initiateBatchTransferRequest);
		}catch (ServiceException e){
			log.error("initiateBatchTransfer:",e.getErrorMessage());
		}
//		log.info("initiateBatchTransfer:", JSONObject.toJSONString(response));
		if(response.getBatchStatus().equals("ACCEPTED")){
			log.info("initiateBatchTransfer:",response.getBatchStatus());
		}
		log.error("initiateBatchTransfer:",response.getBatchStatus());
	}

	/**
	 * 传入apiclient_key内容返回PrivateKey
	 * @param apiclient_key
	 * @return
	 */
	private static PrivateKey getPrivateKey(String apiclient_key){
		return PemUtil.loadPrivateKeyFromString(apiclient_key);
	}

}
