package mx.florinda.cardapio;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class DesserializadorPix {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        try (FileInputStream fis = new FileInputStream("pix.ser");
            ObjectInputStream ois = new ObjectInputStream(fis)) {
            Pix pix = (Pix) ois.readObject();
            System.out.println(pix);
            System.out.println(pix.getChaveDestino());
        }

    }
}
