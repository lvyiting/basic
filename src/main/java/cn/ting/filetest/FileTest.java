package cn.ting.filetest;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import cn.hutool.core.io.FileUtil;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

/**
 * @author : lvyiting
 * @date : 2025/07/09
 **/
public class FileTest {
	public static void main(String[] args) throws IOException {
		File temp = new File("D:/temp");
		if (!temp.exists()){
			//新建目录
			temp.mkdir();
		}
		File file = new File("D:/temp/apiclient_key.pem");
		if (!file.exists()){
			//读取resources下的apiclient_key.pem写入文件中
			Resource resource = new DefaultResourceLoader().getResource(ResourceLoader.CLASSPATH_URL_PREFIX);
			InputStream inputStream = resource.getInputStream();
			FileOutputStream outputStream = new FileOutputStream(file);
			BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
//			bufferedOutputStream.write(bufferedOutputStream.);
		}
	}
}
