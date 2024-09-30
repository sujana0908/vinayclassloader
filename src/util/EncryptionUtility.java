package util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class EncryptionUtility {

    private static final byte KEY = (byte) 0xA3; // Simple XOR key

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java util.EncryptionUtility <input class file> <output encrypted file>");
            return;
        }
        String inputPath = args[0];
        String outputPath = args[1];
        try {
            encryptClassFile(inputPath, outputPath);
            System.out.println("Encryption completed: " + outputPath);
        } catch (IOException e) {
            System.err.println("Error during encryption: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void encryptClassFile(String inputPath, String outputPath) throws IOException {
        byte[] classData = Files.readAllBytes(Paths.get(inputPath));
        byte[] encryptedData = xorEncryptDecrypt(classData);
        Files.createDirectories(Paths.get(outputPath).getParent());
        System.out.println(encryptedData);
        Files.write(Paths.get(outputPath), encryptedData);
    }

    public static byte[] decryptClassData(byte[] encryptedData) {
        return xorEncryptDecrypt(encryptedData);
    }

    private static byte[] xorEncryptDecrypt(byte[] data) {
        byte[] result = new byte[data.length];
        for(int i = 0; i < data.length; i++) {
            result[i] = (byte)(data[i] ^ KEY);
        }
        return result;
    }
}
