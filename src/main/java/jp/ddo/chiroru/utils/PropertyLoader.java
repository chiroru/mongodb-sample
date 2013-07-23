package jp.ddo.chiroru.utils;

import java.io.IOException;
import java.util.Properties;

/**
 * プロパティをロードするインタフェース規定します.
 * 
 * @author chiroru_0130@yahoo.co.jp
 * @since 1.0.0
 * @see {@link CachingPropertyLoader}
 * @see {@link ClassPathPropertyLoader}
 */
public interface PropertyLoader {

    /**
     * 指定したリソースの内容を {@link Properties} に変換し返却します.
     * @param path ロード対象のリソースのパスを指定します.
     * @return ロードしたリソースファイルの内容を {@link Properties} として返却します. 
     * @throws IOException リソースのロードに失敗した場合にスローされます.
     */
    Properties load(String path) throws IOException;
}
