package tannh;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.internal.http.loader.DefaultSdkHttpClientBuilder;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.translate.TranslateClient;
import software.amazon.awssdk.services.translate.model.TranslateTextRequest;
import software.amazon.awssdk.services.translate.model.TranslateTextResponse;

/**
 * 
 * @author HuyTan
 * 
 *         AWS Document
 *         https://docs.aws.amazon.com/translate/latest/dg/API_TranslateText.html
 * 
 *         Sample URL
 *         https://docs.aws.amazon.com/code-samples/latest/catalog/javav2-translate-pom.xml.html
 *         https://docs.aws.amazon.com/code-samples/latest/catalog/javav2-translate-src-main-java-com-example-translate-TranslateText.java.html
 *
 */
public class TranslateSample {
	// AWS Config
	static final String AWS_ACCESS_KEY = "";
	static final String AWS_SECRET_KEY = "";

	public static void main(String[] args) {
		// print output
		System.out.println(translate("こんにちは！", "ja", "en"));
	}

	/**
	 * Translate
	 * 
	 * @param content
	 * @param fromLanguage
	 * @param toLanguage
	 * @return
	 */
	public static String translate(String content, String fromLanguage, String toLanguage) {
		// create AWS Credential from Access key and secret key
		AwsCredentials cred = AwsBasicCredentials.create(AWS_ACCESS_KEY, AWS_SECRET_KEY);

		// create credential provider
		StaticCredentialsProvider credProvider = StaticCredentialsProvider.create(cred);

		// create AWS SDK http client builder
		DefaultSdkHttpClientBuilder httpBuilder = new DefaultSdkHttpClientBuilder();

		// build Amazon Translate client
		TranslateClient translateClient = TranslateClient.builder()
				// region
				.region(Region.AP_NORTHEAST_1)
				// credential provider
				.credentialsProvider(credProvider)
				// http client builder
				.httpClientBuilder(httpBuilder)
				// final step
				.build();

		// build translate request
		TranslateTextRequest request = TranslateTextRequest.builder()
				// content
				.text(content)
				// from language
				.sourceLanguageCode(fromLanguage)
				// to language
				.targetLanguageCode(toLanguage)
				// final step
				.build();

		// call Translate API
		TranslateTextResponse response = translateClient.translateText(request);
		return response.translatedText();
	}
}
