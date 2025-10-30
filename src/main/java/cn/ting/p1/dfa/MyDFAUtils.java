package cn.ting.p1.dfa;

import java.util.HashMap;
import java.util.Map;
import cn.hutool.core.util.StrUtil;

/**
 * 自我实现DFA算法
 * @author : lvyiting
 * @date : 2025/07/31
 **/
public class MyDFAUtils {

	private static Map<String, Object> sensitiveWordMap=null;

	private MyDFAUtils(String keywords) {
		if (StrUtil.isNotBlank(keywords)){
			initSensitiveWordMap(keywords);
		}
	}

	/**
	 * 初始化敏感词库
	 * @param keywords 关键词
	 * @return
	 */
	private void initSensitiveWordMap(String keywords) {
		String[] keyword = keywords.split(",");
		Map<String, Object> nowMap = new HashMap<>(keyword.length);
		for (String key : keyword) {
			for (int i = 0; i < key.length(); i++) {
				String keyChar = String.valueOf(key.charAt(i));
				Map<String, Object> wordMap = (Map<String, Object>) nowMap.get(keyChar);
				if (wordMap == null){
					wordMap = new HashMap<>();
					wordMap.put("isEnd", "0");
					nowMap.put(keyChar, wordMap);
				}
				nowMap = wordMap;
				//是否已经到最后一个了
				if (i==key.length()-1){
					nowMap.put("isEnd", "1");
				}
			}
		}
	}

	public static void main(String[] args) {
		DFAUtils dfaUtils = new DFAUtils("毒品,政治,淫秽,色情,算命,算卦,神仙,保佑,玉皇大帝,王母,鬼怪,精灵,如来,佛祖,灶神,门神,大仙,魔鬼,地狱,报应,性生活,性交,生殖器,赌博,迷信,恐怖,暴力,丑恶,招测试");
		System.out.println(dfaUtils.sensitiveWordMap);
	}
}
