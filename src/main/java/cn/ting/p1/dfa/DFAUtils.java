package cn.ting.p1.dfa;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import cn.hutool.core.util.StrUtil;

/**
 * Description: DFA匹配工具类
 *
 * @author Wenhao.Li
 * Created at 2024/5/17 17:33
 */
public class DFAUtils {

	//短匹配规则，如：敏感词库["广告","广告词"]，语句："我是广告词"，匹配结果：我是[广告]
	public static final int SHORT_MATCH = 1;
	//长匹配规则，如：敏感词库["广告","广告词"]，语句："我是广告词"，匹配结果：我是[广告词]
	public static final int LONG_MATCH = 2;

	/**
	 * 词库
	 */
	public HashMap<String, Object> sensitiveWordMap = null;

	public DFAUtils(String keywords) {
		if (StrUtil.isNotBlank(keywords)) {
			initSensitiveWordMap(keywords);
		}
	}

	/**
	 * 初始化敏感词库
	 * words:敏感词，多个用英文逗号分隔
	 */
	@SuppressWarnings("unchecked")
	private void initSensitiveWordMap(String words) {
		String[] w = words.split(",");
		sensitiveWordMap = new HashMap<>(w.length);
		Map<String, Object> nowMap;
		for (String key : w) {
			nowMap = sensitiveWordMap;
			for (int i = 0; i < key.length(); i++) {
				//转换成char型
				String keyChar = String.valueOf(key.charAt(i));
				//库中获取关键字
				Map<String, Object> wordMap = (Map<String, Object>) nowMap.get(keyChar);
				//如果不存在新建一个，并加入词库
				if (wordMap == null) {
					wordMap = new HashMap<>();
					wordMap.put("isEnd", "0");
					nowMap.put(keyChar, wordMap);
				}
				nowMap = wordMap;
				if (i == key.length() - 1) {
					//最后一个
					nowMap.put("isEnd", "1");
				}
			}
		}
	}

	/**
	 * 判断文字是否包含敏感字符
	 *
	 * @return 若包含返回true，否则返回false
	 */
	public boolean contains(String txt, int matchType) {
		for (int i = 0; i < txt.length(); i++) {
			int matchFlag = checkSensitiveWord(txt, i, matchType); //判断是否包含敏感字符
			if (matchFlag > 0) {    //大于0存在，返回true
				return true;
			}
		}
		return false;
	}


	/**
	 * 沿着文本字符挨个往后检索文字中的敏感词
	 */
	public Set<String> getSensitiveWord(String txt, int matchType) {
		Set<String> sensitiveWordList = new HashSet<>();
		for (int i = 0; i < txt.length(); i++) {
			//判断是否包含敏感字符
			int length = checkSensitiveWord(txt, i, matchType);
			if (length > 0) {//存在,加入list中
				sensitiveWordList.add(txt.substring(i, i + length));
				//指针沿着文本往后移动敏感词的长度
				//也就是一旦找到敏感词，加到列表后，越过这个词的字符，继续往下搜索
				//但是必须减1，因为for循环会自增，如果不减会造成下次循环跳格而忽略字符
				//这会造成严重误差
				i = i + length - 1;
			}
			//如果找不到，i就老老实实一个字一个字的往后移动，作为begin进行下一轮
		}

		return sensitiveWordList;
	}


	/**
	 * 从第beginIndex个字符的位置，往后查找敏感词
	 * 如果找到，返回敏感词字符的长度，不存在返回0
	 * 这个长度用于找到后提取敏感词和后移指针，是个性能关注点
	 */
	@SuppressWarnings("unchecked")
	private int checkSensitiveWord(String txt, int beginIndex, int matchType) {
		//敏感词结束标识位：用于敏感词只有1位的情况
		boolean flag = false;
		//匹配到的敏感字的个数，也就是敏感词长度
		int length = 0;
		String word;
		//从根Map开始查找
		Map<String, Object> nowMap = sensitiveWordMap;
		for (int i = beginIndex; i < txt.length(); i++) {
			//被判断语句的第i个字符开始
			word = String.valueOf(txt.charAt(i));
			//获取指定key，并且将敏感库指针指向下级map
			nowMap = (Map<String, Object>) nowMap.get(word);
			if (nowMap != null) {//存在，则判断是否为最后一个
				//找到相应key，匹配长度+1
				length++;
				//如果为最后一个匹配规则,结束循环，返回匹配标识数
				if ("1".equals(nowMap.get("isEnd"))) {
					//结束标志位为true
					flag = true;
					//短匹配，直接返回,长匹配还需继续查找
					if (SHORT_MATCH == matchType) {
						break;
					}
				}
			} else {
				//敏感库不存在，直接中断
				break;
			}
		}
		if (length < 2 || !flag) {
			//长度必须大于等于1才算是词，字的话就不必这么折腾了
			length = 0;
		}
		return length;
	}

	public static void main(String[] args) {
		DFAUtils dfaUtils = new DFAUtils("毒品,政治,淫秽,色情,算命,算卦,神仙,保佑,玉皇大帝,王母,鬼怪,精灵,如来,佛祖,灶神,门神,大仙,魔鬼,地狱,报应,性生活,性交,生殖器,赌博,迷信,恐怖,暴力,丑恶,招测试");
		System.out.println(dfaUtils.sensitiveWordMap);
	}
}

