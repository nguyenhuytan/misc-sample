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
 * AWS Translate API Sample
 * 
 * Repo: https://mvnrepository.com/artifact/software.amazon.awssdk/translate
 * 
 * @author nguyenhuytan
 *
 */

public class TranslateUtils {
	// AWS Config
	static final String AWS_ACCESS_KEY = "";
	static final String AWS_SECRET_KEY = "";

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
