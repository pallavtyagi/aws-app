package com.s3util.s3utility;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.transfer.Transfer;
import com.amazonaws.services.s3.transfer.TransferProgress;

public class XferMgrProgress {


    public static void waitForCompletion(Transfer xfer)
    {
        try {
            xfer.waitForCompletion();
        } catch (AmazonServiceException e) {
            System.err.println("Amazon service error: " + e.getMessage());
            System.exit(1);
        } catch (AmazonClientException e) {
            System.err.println("Amazon client error: " + e.getMessage());
            System.exit(1);
        } catch (InterruptedException e) {
            System.err.println("Transfer interrupted: " + e.getMessage());
            System.exit(1);
        }
    }

    public static void showTransferProgress(Transfer xfer)
    {
        System.out.println("Uploading to S3...");
        printProgressBar(0.0);
        do {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                return;
            }
            TransferProgress progress = xfer.getProgress();
            double pct = progress.getPercentTransferred();
            eraseProgressBar();
            printProgressBar(pct);
        } while (!xfer.isDone());
        Transfer.TransferState xfer_state = xfer.getState();
        System.out.println(": " + xfer_state);
    }

    public static void printProgressBar(double pct)
    {
        final int bar_size = 40;
        final String empty_bar = "                                        ";
        final String filled_bar = "########################################";
        int amt_full = (int)(bar_size * (pct / 100.0));
        System.out.format("  [%s%s]", filled_bar.substring(0, amt_full),
                empty_bar.substring(0, bar_size - amt_full));
    }

    public static void eraseProgressBar()
    {
        final String erase_bar = "\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b";
        System.out.format(erase_bar);
    }

}
