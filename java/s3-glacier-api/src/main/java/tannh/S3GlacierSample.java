package tannh;

import java.io.File;
import java.nio.file.Paths;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.glacier.GlacierClient;
import software.amazon.awssdk.services.glacier.model.GlacierException;
import software.amazon.awssdk.services.glacier.model.UploadArchiveRequest;
import software.amazon.awssdk.services.glacier.model.UploadArchiveResponse;

/**
 * 
 * @author HuyTan
 * 
 *         AWS Document
 *         https://docs.aws.amazon.com/amazonglacier/latest/dev/uploading-archive-single-operation.html
 * 
 *         Sample URL
 *         https://docs.aws.amazon.com/code-samples/latest/catalog/javav2-glacier-pom.xml.html
 *         https://docs.aws.amazon.com/code-samples/latest/catalog/javav2-glacier-src-main-java-com-example-glacier-UploadArchive.java.html
 *
 *         Note: I tried building project on Java Compiler [11], but
 *         GlacierClient.builder() not found. So I had to change Java Compiler
 *         from 11 to 1.8
 *
 */
public class S3GlacierSample {
	private static String VAULT_NAME = "";
	private static String ARCHIVE_TO_UPLOAD = "D:\\20200806_010453.dat";

	private static String AWS_ACCESS_KEY = "";
	private static String AWS_SECRET_KEY = "";

	public static void main(String[] args) {
		// create AWS Credential from Access key and secret key
		AwsCredentials cred = AwsBasicCredentials.create(AWS_ACCESS_KEY, AWS_SECRET_KEY);

		// create credential provider
		StaticCredentialsProvider credProvider = StaticCredentialsProvider.create(cred);

		GlacierClient client = GlacierClient.builder().region(Region.AP_NORTHEAST_1).credentialsProvider(credProvider)
				.build();

		File file = new File(ARCHIVE_TO_UPLOAD);
		if (!file.exists()) {
			System.out.println("File not found !!!");
			return;
		}

		// Get an SHA-256 tree hash value.
		String checkVal = ComputeUtils.computeSHA256(file);
		try {
			UploadArchiveRequest uploadRequest = UploadArchiveRequest.builder().vaultName(VAULT_NAME).checksum(checkVal)
					.build();

			UploadArchiveResponse res = client.uploadArchive(uploadRequest, Paths.get(ARCHIVE_TO_UPLOAD));
			System.out.println("Uploaded !!!");
			System.out.println("  Archive ID: " + res.archiveId());
			System.out.println("  Location  : " + res.location());
			System.out.println("  Checksum  : " + res.checksum());

		} catch (GlacierException e) {
			System.err.println(e.awsErrorDetails().errorMessage());
		}
	}

}
