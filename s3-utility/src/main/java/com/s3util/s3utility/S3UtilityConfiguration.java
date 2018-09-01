package com.s3util.s3utility;


import com.amazonaws.services.s3.transfer.MultipleFileUpload;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Configuration
public class S3UtilityConfiguration {

    @Autowired
    private AwsS3Properties awsS3Properties;

    @Bean
    public TransferManager transferManager() {
        return TransferManagerBuilder.defaultTransferManager();
    }

    @Bean(name = "awsS3Writer")
    public BiConsumer<String, String> writeToAwsS2() {
        return (filePath, bucketPrefix) -> {
            MultipleFileUpload multipleFileUpload = transferManager().uploadDirectory(awsS3Properties.getS3BucketName(), bucketPrefix, new File(filePath), true);
            XferMgrProgress.showTransferProgress(multipleFileUpload);
            XferMgrProgress.waitForCompletion(multipleFileUpload);
        };

    }

}
