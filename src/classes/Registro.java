package classes;

import java.io.IOException;
import java.io.RandomAccessFile;

public class Registro {
    
    public final int tf=1022;
    int numero;
    private char trash[] = new char[tf];
    
    public Registro(){}
    
    public Registro(int numero){
        this.numero=numero;
        for (int i=0 ; i<tf ; i++)
            trash[i]='X';
    }

    public void gravaNoArq(RandomAccessFile arquivo){
        try{
            arquivo.writeInt(numero);
            for(int i=0 ; i<tf ; i++)
                arquivo.writeChar(trash[i]);
        }catch(IOException e){}
    }
    
    public void leDoArq(RandomAccessFile arquivo){
        try{
            numero = arquivo.readInt();
            for(int i=0 ; i<tf ; i++)
                trash[i]=arquivo.readChar();
        }catch(IOException e){}
    }
    
    public int getNumero(){
        return (numero);
    }

    static int length(){
        return(2048);
    }
    
    public void exibirReg()
    {
        int i;
        System.out.println("Número: " + this.numero);
    }
}

