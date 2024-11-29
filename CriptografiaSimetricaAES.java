package CriptografiaSimetricaAES;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Scanner;

public class CriptografiaSimetricaAES {

    // Método para gerar uma chave secreta de 128 bits
    private static SecretKey gerarChaveSecreta() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128); // Configura a chave para 128 bits
        return keyGenerator.generateKey();
    }

    // Método para criptografar uma mensagem
    private static String criptografar(String mensagem, SecretKey chave) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, chave);
        byte[] mensagemCriptografada = cipher.doFinal(mensagem.getBytes("UTF-8"));
        return Base64.getEncoder().encodeToString(mensagemCriptografada); // Converte para Base64 para facilitar a leitura
    }

    // Método para descriptografar uma mensagem
    private static String descriptografar(String mensagemCriptografada, SecretKey chave) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, chave);
        byte[] mensagemDecodificada = Base64.getDecoder().decode(mensagemCriptografada);
        byte[] mensagemOriginal = cipher.doFinal(mensagemDecodificada);
        return new String(mensagemOriginal, "UTF-8");
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            // Geração de chave secreta
            SecretKey chaveSecreta = gerarChaveSecreta();
            System.out.println("Chave secreta gerada com sucesso!");

            // Exibe a chave secreta em Base64 (apenas para referência)
            String chaveEmBase64 = Base64.getEncoder().encodeToString(chaveSecreta.getEncoded());
            System.out.println("Chave secreta (Base64): " + chaveEmBase64);

            // Entrada de mensagem para criptografar
            System.out.print("Digite a mensagem para criptografar: ");
            String mensagemOriginal = scanner.nextLine();

            // Criptografar a mensagem
            String mensagemCriptografada = criptografar(mensagemOriginal, chaveSecreta);
            System.out.println("Mensagem criptografada: " + mensagemCriptografada);

            // Entrada de mensagem criptografada para descriptografar
            System.out.print("Digite a mensagem criptografada para descriptografar: ");
            String mensagemParaDescriptografar = scanner.nextLine();

            // Descriptografar a mensagem
            String mensagemDescriptografada = descriptografar(mensagemParaDescriptografar, chaveSecreta);
            System.out.println("Mensagem descriptografada: " + mensagemDescriptografada);
        } catch (Exception e) {
            System.err.println("Ocorreu um erro: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
