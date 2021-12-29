package classes;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Random;

public class Arquivo_Java {
    
    private String nomearquivo;
    private RandomAccessFile arquivo;
    private int comp, mov;

    public Arquivo_Java() {
    }
    
    public Arquivo_Java(String nomearquivo){
        nomearquivo=nomearquivo;
        try
        {
            arquivo = new RandomAccessFile(nomearquivo, "rw");
        } catch (IOException e)
        { }
    }
    
    public RandomAccessFile getFile() {
        return arquivo;
    }
    
    public void initComp(){
        this.comp = 0;
    }
    
    public int getComp(){
        return this.comp;
    }
    
    public void initMov(){
        this.mov = 0;
    }
    
    public int getMov(){
        return this.mov;
    }
    
    public String getNome()
    {
        return nomearquivo;
    }
    
    public Arquivo_Java copiaArquivo(String nomenovo)
    {
        this.seekArq(0);
        Arquivo_Java novo = new Arquivo_Java(nomenovo);
        Registro aux = new Registro();
        novo.comp = 0;
        novo.mov = 0;
        aux.leDoArq(arquivo);
        while(!this.eof(arquivo))
        {
            aux.gravaNoArq(novo.getFile());
            aux.leDoArq(arquivo);
        }
        aux.gravaNoArq(novo.getFile());
        return novo;
    }
   
    public void copiaArquivo(RandomAccessFile arquivoOrigem) throws IOException{
        Registro regCopia = new Registro();
        seekArq(0); 
        arquivoOrigem.seek(0);
        regCopia.leDoArq(arquivoOrigem);
        while(!eof(arquivoOrigem)){
            regCopia.gravaNoArq(arquivo);
            regCopia.leDoArq(arquivoOrigem);
        }
        regCopia.gravaNoArq(arquivo);
    }
    
    
    public void criaOrdenado() throws FileNotFoundException, IOException{
        Registro reg = new Registro();
        int cont=0;
        while(cont<512){
            reg.numero = cont;
            inserirRegNoFinal(reg);
            cont++;
        }
    }
    
    public void criaInverso() throws FileNotFoundException, IOException{
        Registro reg = new Registro();
        int cont=512;
        while(cont>0){
            reg.numero = cont;
            inserirRegNoFinal(reg);
            cont--;
        }
    }
    
    private boolean buscaNoArq(int codigo) throws FileNotFoundException, IOException{
        Registro aux = new Registro();
        seekArq(0);
        aux.leDoArq(arquivo);
        while (!this.eof(arquivo) && aux.getNumero()!=codigo){
            aux.leDoArq(arquivo);
        }
        if(aux.getNumero()==codigo)
            return true;
        return false;
    }
    
    public void criaAleatorio() throws IOException{
        Registro reg = new Registro();
        int cont=0;
        Random var = new Random();
        while(cont<512){
            reg.numero = var.nextInt(1024)+1;
                inserirRegNoFinal(reg);
                cont++;
        }
    }
    
    public void truncate(long pos){
        try
        {
            arquivo.setLength(pos * Registro.length());
        } catch (IOException exc)
        { }
    }
    
    public boolean eof(RandomAccessFile arq){
        boolean retorno = false;
        try
        {
            if (arq.getFilePointer() == arq.length())
                retorno = true;                               
        } catch (IOException e)
        { }
        return (retorno);
    }
    
    public void seekArq(int pos){
        try
        {
            arquivo.seek(pos * Registro.length());
        } catch (IOException e)
        { }
    }
    
    public int filesize() throws IOException {
        return (int) (arquivo.length()/Registro.length());
    }
    
    public void inserirRegNoFinal(Registro reg) throws IOException
    {
        seekArq(filesize());
        reg.gravaNoArq(arquivo);
    }
    
    public void exibirArq(){
        int i;
        Registro aux = new Registro();
        seekArq(0);
        i = 0;
        while (!this.eof(arquivo))
        {
            aux.leDoArq(arquivo);
            aux.exibirReg();
            i++;
        }
    }
    
    public void insercaoDireta() throws IOException{
        int pos;
        Registro auxReg = new Registro();
        Registro auxReg2 = new Registro();
        for(int i=1; i<filesize(); i++){
            seekArq(i);
            auxReg2.leDoArq(arquivo);
            pos = i;
            seekArq(pos-1); 
            auxReg.leDoArq(arquivo);
            this.comp++;
            while(pos>0 && auxReg2.getNumero()<auxReg.getNumero()){
                seekArq(pos); auxReg.gravaNoArq(arquivo);
                pos--;
                this.mov++;
                if(pos>0){
                    seekArq(pos-1); 
                    auxReg.leDoArq(arquivo);
                }
                this.comp++;
            }
            seekArq(pos);
            auxReg2.gravaNoArq(arquivo);
            this.mov++;
        }
    }
    
    private int buscaBinaraInsercao(int codigo, int f) throws IOException{
        Registro aux = new Registro();
        int ini=0, meio, fim = f;
        meio = fim/2;
        seekArq(meio); aux.leDoArq(arquivo);
        this.comp++;
        while(ini<fim && codigo!=aux.getNumero()){
            this.comp++;
            if(codigo>aux.getNumero())
                ini = meio+1;
            else
                fim = meio;
            meio = (ini+fim)/2;
            seekArq(meio); aux.leDoArq(arquivo);
            this.comp++;
        }
        return meio;
    }

    public void insercaoBinaria() throws IOException{
        Registro aux = new Registro();
        Registro aux2 = new Registro();
        int pos;
        for(int i=1; i<filesize(); i++){
            seekArq(i); aux.leDoArq(arquivo);
            pos = buscaBinaraInsercao(aux.getNumero(), i);
            this.comp++;
            for(int j=i; j>pos; j--){
                this.mov++;
                seekArq(j-1); aux2.leDoArq(arquivo);
                seekArq(j); aux2.gravaNoArq(arquivo);
            }
            this.mov++;
            seekArq(pos); aux.gravaNoArq(arquivo);
        }
    }
    
    public void selecaoDireta() throws IOException{
        int posMenor;
        int mov = 0, comp = 0;
        Registro aux = new Registro();
        Registro menor = new Registro();
        for(int i=0; i<filesize()-1; i++){
            posMenor = i;
            seekArq(i);
            menor.leDoArq(arquivo);
            for(int j=i+1; j<filesize(); j++){
                seekArq(j);
                aux.leDoArq(arquivo);
                this.comp++;
                if(aux.getNumero()<menor.getNumero()){
                    posMenor = j;
                    seekArq(j);
                    menor.leDoArq(arquivo);
                }
            }
            seekArq(i);
            aux.leDoArq(arquivo);
            seekArq(i);
            menor.gravaNoArq(arquivo); this.mov++;
            seekArq(posMenor);
            aux.gravaNoArq(arquivo); this.mov++;
        }
    }
    
    public void bubbleSort() throws IOException{
        Registro aux = new Registro();
        Registro aux1 = new Registro();
        Registro aux2 = new Registro();
        int tamArq = filesize();
        this.comp++;
        while(tamArq>1){
            for(int j=0; j<tamArq-1; j++){
                seekArq(j); aux1.leDoArq(arquivo);
                seekArq(j+1); aux2.leDoArq(arquivo);
                this.comp++;
                if(aux1.getNumero()>aux2.getNumero()){
                    aux = aux1;
                    seekArq(j); aux2.gravaNoArq(arquivo);
                    seekArq(j+1); aux.gravaNoArq(arquivo);
                    this.mov+=2;
                }
            }
            tamArq -= 1;
            this.comp++;
        }
    }
    
    public void shakeSort() throws IOException{
        Registro aux = new Registro();
        Registro aux1 = new Registro();
        Registro aux2 = new Registro();
        int inicio=0, fim=filesize();
        int mov=0, comp=0;
        this.comp++;
        while(fim>inicio){
            for(int i=inicio; i<fim-1; i++){
                seekArq(i); aux1.leDoArq(arquivo);
                seekArq(i+1); aux2.leDoArq(arquivo);
                this.comp++;
                if(aux1.getNumero() > aux2.getNumero()){
                    aux = aux1;
                    seekArq(i); aux2.gravaNoArq(arquivo);
                    seekArq(i+1); aux.gravaNoArq(arquivo);
                    this.mov+=2;
                }
            }
            fim--;
            for(int i=fim; i>inicio; i--){
                seekArq(i); aux1.leDoArq(arquivo);
                seekArq(i-1); aux2.leDoArq(arquivo);
                this.comp++;
                if(aux1.getNumero() < aux2.getNumero()){
                    aux = aux1;
                    seekArq(i); aux2.gravaNoArq(arquivo);
                    seekArq(i-1); aux.gravaNoArq(arquivo);
                    this.mov+=2;
                }
            }
            inicio++;
            this.comp++;
        }
    }
    
    public void heapSort() throws IOException{
        int posPai, tl2=filesize(), posFE, posFD, posMaiorF;
        Registro FE = new Registro();
        Registro FD = new Registro();
        Registro maiorF = new Registro();
        Registro aux = new Registro();
        Registro pai = new Registro();
        this.comp++;
        while(tl2>1){
            posPai = tl2/2-1;
            this.comp++;
            while(posPai>=0){
                posFE = posPai+posPai+1;
                posFD = posFE+1;
                seekArq(posFE); FE.leDoArq(arquivo);
                seekArq(posFD); FD.leDoArq(arquivo);
                this.comp++;
                if(posFD<tl2 && FD.getNumero()>FE.getNumero())
                    posMaiorF = posFD;
                else
                    posMaiorF = posFE;
                seekArq(posMaiorF); maiorF.leDoArq(arquivo);
                seekArq(posPai); pai.leDoArq(arquivo);
                this.comp++;
                if(maiorF.getNumero() > pai.getNumero()){
                    aux = pai;
                    seekArq(posPai); maiorF.gravaNoArq(arquivo);
                    seekArq(posMaiorF); aux.gravaNoArq(arquivo);
                    this.mov+=2;
                }
                posPai--;
                this.comp++;
            }
            seekArq(0); aux.leDoArq(arquivo);
            seekArq(tl2-1); FE.leDoArq(arquivo);
            seekArq(0); FE.gravaNoArq(arquivo);
            seekArq(tl2-1); aux.gravaNoArq(arquivo);
            this.mov+=2;
            tl2--;
            this.comp++;
        }
    }
    
    public void shellSort() throws IOException{
        int dist=4;
        Registro aux = new Registro();
        Registro aux2 = new Registro();
        this.comp++;
        while(dist>0){
            for(int i=0; i<dist; i++){
                int j = i;
                this.comp++;
                while(j+dist < filesize()){
                    seekArq(j); aux.leDoArq(arquivo);
                    seekArq(j+dist); aux2.leDoArq(arquivo);
                    this.comp++;
                    if(aux.getNumero() > aux2.getNumero()){
                        seekArq(j); aux2.gravaNoArq(arquivo);
                        seekArq(j+dist); aux.gravaNoArq(arquivo);
                        this.mov+=2;
                        int k = j;
                        seekArq(k); aux.leDoArq(arquivo);
                        seekArq(k-dist); aux2.leDoArq(arquivo);
                        this.comp++;
                        while(k-dist >= i && aux.getNumero()<aux2.getNumero()){
                            seekArq(k); aux2.gravaNoArq(arquivo);
                            seekArq(k-dist); aux.gravaNoArq(arquivo);
                            this.mov+=2;
                            k-=dist;
                            seekArq(k); aux.leDoArq(arquivo);
                            seekArq(k-dist); aux2.leDoArq(arquivo);
                            this.comp++;
                        }
                    }
                    j+=dist;
                    this.comp++;
                }
            }
            dist/=2;
            this.comp++;
        }
    }
    
    public void ordQuickSemPivo() throws IOException{
        quickSP(0, filesize()-1);
    }
    
    public void quickSP(int ini, int fim){
        int i = ini, j = fim;
        boolean flag = true;
        Registro aux = new Registro();
        Registro reg = new Registro();
        Registro reg2 = new Registro();
        
        this.comp++;
        while(i<j){
            if(flag){
                seekArq(i); reg.leDoArq(arquivo);
                seekArq(j); reg2.leDoArq(arquivo);
                this.comp++;
                while(i<j && reg.getNumero()<=reg2.getNumero()){
                    i++;
                    seekArq(i); reg.leDoArq(arquivo);
                    this.comp++;
                }
            }else{
                this.comp++;
                while(j>i && reg2.getNumero() >= reg.getNumero()){
                    j--;
                    seekArq(j); reg2.leDoArq(arquivo);
                    this.comp++;
                }
            }
            aux = reg;
            seekArq(i); reg2.gravaNoArq(arquivo);
            seekArq(j); aux.gravaNoArq(arquivo);
            this.mov+=2;
            flag = !flag;
            this.comp++;
        }
        this.comp++;
        if(ini < i-1)
            quickSP(ini, i-1);
        this.comp++;
        if(j+1 < fim)
            quickSP(j+1, fim);
    }
    
    public void ordQuickComPivo() throws IOException{
        quickSP(0, filesize()-1);
    }
    
    public void quickCP(int ini, int fim){
        int i = ini, j = fim;
        boolean flag = true;
        
        Registro pivo = new Registro();
        Registro reg = new Registro();
        Registro reg2 = new Registro();
        
        seekArq((i+j)/2); pivo.leDoArq(arquivo);
        
        this.comp++;
        while(i<j){
            seekArq(i); reg.leDoArq(arquivo);
            this.comp++;
            while(reg.getNumero() < pivo.getNumero()){
                i++;
                reg.leDoArq(arquivo);
                this.comp++;
            }
            
            seekArq(j); reg2.leDoArq(arquivo);
            this.comp++;
            while(reg2.getNumero() > pivo.getNumero()){
                j++;
                reg2.leDoArq(arquivo);
                this.comp++;
            }
            
            this.comp++;
            if(i<=j){
                pivo = reg;
                seekArq(i); reg2.gravaNoArq(arquivo);
                seekArq(j); pivo.gravaNoArq(arquivo);
                this.mov+=2;
            }   
            this.comp++;
        }
        this.comp++;
        if(ini<j)
            quickCP(ini, j);
        this.comp++;
        if(i<fim)
            quickCP(i, fim);
    }
    
    public void merge1() throws IOException
    {
        Arquivo_Java arq = this.copiaArquivo("Merge1_ordenado.txt");
        this.mov=0;
        this.comp=0;
        Arquivo_Java arq1 = new Arquivo_Java("Temp1.txt");
        Arquivo_Java arq2 = new Arquivo_Java("Temp2.txt");
        int seq = 1;
        while(seq<arq.filesize())
        {
            arq1.truncate(0);
            arq2.truncate(0);
            arq.particao1(arq1, arq2);
            arq.fusao1(arq1, arq2, seq);
            seq = seq*2;
        }
        this.comp=arq.getComp();
        this.mov=arq.getMov();
    }
    
    public void particao1(Arquivo_Java arq1, Arquivo_Java arq2) throws IOException
    {
        int tam = this.filesize()/2;
        Registro aux = new Registro();
        for(int i=0;i<tam;i++)
        {
            this.seekArq(i);
            aux.leDoArq(arquivo);
            arq1.seekArq(i);
            aux.gravaNoArq(arq1.getFile());
            this.mov+=2;
            
            this.seekArq(i+tam);
            aux.leDoArq(arquivo);
            arq2.seekArq(i);
            aux.gravaNoArq(arq2.getFile());
            this.mov+=2;
        }
    }
    
    public void fusao1(Arquivo_Java arq1, Arquivo_Java arq2, int seq) throws IOException
    {
        int i=0,j=0,k=0,auxseq=seq;
        Registro auxi = new Registro();
        Registro auxj = new Registro();
        while(k<this.filesize())
        {
            while(i<seq && j<seq)
            {
                arq1.seekArq(i);
                auxi.leDoArq(arq1.getFile());
                arq2.seekArq(j);
                auxj.leDoArq(arq2.getFile());
                this.seekArq(k);
                this.comp+=1;
                if(auxi.getNumero()<=auxj.getNumero())
                {
                    auxi.gravaNoArq(arquivo);
                    this.mov+=2;
                    i++;
                    k++;
                }
                else
                {
                    auxj.gravaNoArq(arquivo);
                    this.mov+=2;
                    j++;
                    k++;
                }
            }
            while(i<seq)
            {
                arq1.seekArq(i);
                auxi.leDoArq(arq1.getFile());
                this.seekArq(k);
                auxi.gravaNoArq(arquivo);
                this.mov+=2;
                i++;
                k++;
            }
            while(j<seq)
            {
                arq2.seekArq(j);
                auxj.leDoArq(arq2.getFile());
                this.seekArq(k);
                auxj.gravaNoArq(arquivo);
                this.mov+=2;
                j++;
                k++;
            }
            seq = seq+auxseq;
        }
    }
    
    public void merge2() throws IOException
    {
        Arquivo_Java arq = this.copiaArquivo("Merge2_ordenado.txt");
        this.mov=0;
        this.comp=0;
        Arquivo_Java arqaux = new Arquivo_Java("Arq_aux.txt");
        arq.particao2(0, arq.filesize()-1, arqaux);
        this.comp=arq.getComp();
        this.mov=arq.getMov();
    }   
    
    public void particao2(int esq, int dir, Arquivo_Java arqaux)
    {
        int meio;
        if(esq<dir)
        {
            meio = (esq+dir)/2;
            this.particao2(esq, meio, arqaux);
            this.particao2(meio+1, dir, arqaux);
            this.fusao2(esq, meio, meio+1, dir, arqaux);
        }
    }
    
    public void fusao2(int ini1, int fim1, int ini2, int fim2, Arquivo_Java arqaux)
    {
        int i=ini1, j=ini2, k=0;
        Registro auxi = new Registro();
        Registro auxj = new Registro();
        while(i<=fim1 && j<=fim2)
        {
            this.seekArq(i);
            auxi.leDoArq(arquivo);
            this.seekArq(j);
            auxj.leDoArq(arquivo);
            arqaux.seekArq(k);
            this.comp+=1;
            if(auxi.getNumero()<auxj.getNumero())
            {
                auxi.gravaNoArq(arqaux.getFile());
                this.mov+=2;
                i++;
                k++;
            }
            else
            {
                auxj.gravaNoArq(arqaux.getFile());
                this.mov+=2;
                j++;
                k++;
            }
        }
        while(i<=fim1)
        {
            this.seekArq(i);
            auxi.leDoArq(arquivo);
            arqaux.seekArq(k);
            auxi.gravaNoArq(arqaux.getFile());
            this.mov+=2;
            i++;
            k++;
        }
        while(j<=fim2)
        {
            this.seekArq(j);
            auxj.leDoArq(arquivo);
            arqaux.seekArq(k);
            auxj.gravaNoArq(arqaux.getFile());
            this.mov+=2;
            j++;
            k++;
        }
        for(i=0; i<k; i++)
        {
            arqaux.seekArq(i);
            auxi.leDoArq(arqaux.getFile());
            this.seekArq(i+ini1);
            auxi.gravaNoArq(arquivo);
            this.mov+=2;
        }
    }
    
    public void countingSort() throws IOException
    {
        Arquivo_Java arq = this.copiaArquivo("Counting_ordenado.txt");
        this.mov=0;
        this.comp=0;
        //Cria lista do tamanho do maior elemento
        Lista aux = new Lista();
        Registro reg = new Registro();
        No Nova = new No();
        int maior = 0;
        for(int i=0;i<arq.filesize();i++)
        {
            arq.seekArq(i);
            reg.leDoArq(arq.getFile());
            this.comp+=1;
            if(reg.getNumero()>maior)
                maior = reg.getNumero();
        }
        aux.novaListaCounting(maior+1);
        
        //Adiciona valores na lista criada
        for(int i=0;i<arq.filesize();i++)
        {
            arq.seekArq(i);
            reg.leDoArq(arq.getFile());
            Nova = aux.getIni();
            Nova = aux.AndaNo(Nova, reg.getNumero());
            Nova.setInfo(Nova.getInfo()+1);
        }
        //Ordena na lista verdadeira
        Nova = aux.getIni();
        int j=0;
        for(int i=0;i<arq.filesize();i++)
        {
            while(Nova!=null &&Nova.getInfo()==0)
            {
                Nova = Nova.getProx();
                j++;
            }  
            if(Nova!=null)
                Nova.setInfo(Nova.getInfo()-1);
            Registro novo = new Registro(j);
            arq.seekArq(i);
            novo.gravaNoArq(arq.getFile());
            this.mov+=2;
        }
        aux.inicializa();
    }
    
    public void bucketSort() throws IOException
    {
        Arquivo_Java arq = this.copiaArquivo("Bucket_ordenado.txt");
        this.mov=0;
        this.comp=0;
        
        Lista lis1 = new Lista();
        Lista lis2 = new Lista();
        Lista lis3 = new Lista();
        Lista lis4 = new Lista();
        Lista lis5 = new Lista();
        int i=0;
        Registro aux = new Registro();
        while(i<arq.filesize())
        {
            arq.seekArq(i);
            aux.leDoArq(arq.getFile());
            if (aux.getNumero() < arq.filesize()/5)
            {
                this.comp+=1;
                lis1.inserirFinal(aux.getNumero());
            } else if (aux.getNumero() < (arq.filesize()/5)*2) 
            {
                this.comp+=1;
                lis2.inserirFinal(aux.getNumero());
            } else if (aux.getNumero() < (arq.filesize()/5)*3) 
            {
                this.comp+=1;
                lis3.inserirFinal(aux.getNumero());
            } else if (aux.getNumero() < (arq.filesize()/5)*4) 
            {
                this.comp+=1;
                lis4.inserirFinal(aux.getNumero());
            } else 
            {
                lis5.inserirFinal(aux.getNumero());
            }
            i++;
        }
        
        lis1.insertionSortBucket();
        lis2.insertionSortBucket();
        lis3.insertionSortBucket();
        lis4.insertionSortBucket();
        lis5.insertionSortBucket();
        
        //juncao
        int TL=0;
        arq.fusaoBucket(0, lis1);
        TL += lis1.getQtd();
        arq.fusaoBucket(TL, lis2);
        TL += lis2.getQtd();
        arq.fusaoBucket(TL, lis3);
        TL += lis3.getQtd();
        arq.fusaoBucket(TL, lis4);
        TL += lis4.getQtd();
        arq.fusaoBucket(TL, lis5);
        TL += lis5.getQtd();
      
        this.comp=getComp()+arq.getComp();
        this.mov=getMov()+arq.getMov();
    }
    
    public void fusaoBucket(int pos, Lista aux)
    {
        No pAux = aux.getIni();
        int i=0;
        while(pAux != null)
        {
            Registro novo = new Registro(pAux.getInfo());
            this.seekArq(pos+i);
            novo.gravaNoArq(arquivo);
            this.mov+=2;
            pAux = pAux.getProx();
            i++;
        }
    }
    
    public void radixSort() throws IOException
    {
        Arquivo_Java arq = this.copiaArquivo("Radix_sort.txt");
        this.mov=0;
        this.comp=0;
        int maior = 0,i=0;
        Registro aux = new Registro();
        while(i<arq.filesize())
        {
            arq.seekArq(i);
            aux.leDoArq(arq.getFile());
            this.comp+=1;
            if(aux.getNumero()>maior)
                maior = aux.getNumero();
            i++;
        }
        for(int exp = 1; maior / exp > 0; exp *= 10)
            arq.countingRadix(exp);
        this.comp=getComp()+arq.getComp();
        this.mov=getMov()+arq.getMov();
    }
    
    public void countingRadix(int exp) throws IOException
    {
        int Counting [] = new int [10];
        Registro reg[] = new Registro[this.filesize()];
        Registro aux;
        for(int i=0; i<10; i++)
            Counting[i] = 0;
        
        this.seekArq(0);
        for(int i=0; i<this.filesize(); i++)
        {
            aux = new Registro();
            aux.leDoArq(arquivo);
            Counting[(aux.getNumero()/exp)%10]++;
        }
        
        for (int i = 1; i < 10; i++)
            Counting[i] += Counting[i - 1];
        
        for (int i = this.filesize()-1; i >= 0; i--) 
        {
            aux = new Registro();
            this.seekArq(i);
            aux.leDoArq(arquivo);
            reg[Counting[(aux.getNumero()/exp)%10]-1] = aux;
            Counting[(aux.getNumero()/exp) % 10]--;
        }
        
        this.seekArq(0);
        for (int i = 0; i < this.filesize(); i++)
        {
            reg[i].gravaNoArq(arquivo);
            this.mov+=2;
        }
            
    }
    
    public void combSort() throws IOException
    {
        Arquivo_Java arq = this.copiaArquivo("Comb_sort.txt");
        this.mov=0;
        this.comp=0;
        arq.classificar();
        this.comp=arq.getComp();
        this.mov=arq.getMov();
    }
    
    public void classificar() throws IOException
    {
        int intervalo = (int) (this.filesize()/1.3);
        int i = 0, temp;
        Registro aux = new Registro();
        Registro inter = new Registro();
        while(intervalo > 0 && i != this.filesize()-1)
        {
            i = 0;
            while((i+intervalo) < this.filesize())
            {
                this.seekArq(i);
                aux.leDoArq(arquivo);
                this.seekArq(i+intervalo);
                inter.leDoArq(arquivo);
                this.comp+=1;
                if(aux.getNumero() > inter.getNumero())
                {
                    this.seekArq(i);
                    inter.gravaNoArq(arquivo);
                    this.mov+=2;
                    this.seekArq(i+intervalo);
                    aux.gravaNoArq(arquivo);
                    this.mov+=2;
                }
                i++;
            }
            intervalo = (int) (intervalo / 1.3);
        }
    }
    
    public void gnomeSort() throws IOException
    {
        Arquivo_Java arq = this.copiaArquivo("Gnome_sort.txt");
        this.mov=0;
        this.comp=0;
        int i = 0;
        Registro aux = new Registro();
        Registro ant = new Registro();
        while(i<this.filesize())
        {
            if(i==0)
                i++; 
            
            arq.seekArq(i-1);
            ant.leDoArq(arq.getFile());
            aux.leDoArq(arq.getFile());
            
            this.comp+=1;
            if(aux.getNumero()>=ant.getNumero())
                i++;
            else
            {
                arq.seekArq(i-1);
                aux.gravaNoArq(arq.getFile());
                this.mov+=2;
                ant.gravaNoArq(arq.getFile());
                this.mov+=2;
                i--;
            }
        }
    }
    
    public void timSort() throws IOException
    {
        Arquivo_Java arq = this.copiaArquivo("Tim_sort.txt");
        this.mov=0;
        this.comp=0;
        int i, tam,TL = arq.filesize(), bloco=32, ini, meio, fim;  
        
        for (i = 0; i < TL; i+=bloco)  
            arq.insertionSortTim(i, menor((i+bloco-1),(TL-1)));  
        
        for (tam = bloco; tam < TL; tam = 2*tam){  
            for (ini = 0; ini < TL; ini += 2*tam){  
                meio = ini + tam - 1;  
                fim = arq.menor((ini + 2*tam - 1), (TL-1)); 
                
                if(meio < fim)
                    arq.juncao(ini, meio, fim);  
            }  
        }
        this.comp=arq.getComp();
        this.mov=arq.getMov();
    }
    
    public void insertionSortTim(int ini, int fim)
    {
        int i=ini+1, pos;
        Registro auxpos = new Registro();
        Registro aux = new Registro();
        while(i<=fim)
        {
            pos = i;
            this.seekArq(i);
            aux.leDoArq(arquivo);
            this.seekArq(pos-1);
            auxpos.leDoArq(arquivo);
            this.comp+=1;
            while(pos>ini && aux.getNumero()<auxpos.getNumero())
            {
                this.seekArq(pos);
                auxpos.gravaNoArq(arquivo);
                this.mov+=2;
                pos--;
                this.seekArq(pos-1);
                auxpos.leDoArq(arquivo);
                this.comp+=1;
            }  
            this.seekArq(pos);
            aux.gravaNoArq(arquivo);
            this.mov+=2;
            i++;
        }
    }
    
    
    public int menor(int a, int b)
    {
        if(a<b)
            return a;
        return b;
    }
    
    public void juncao(int ini, int meio, int fim)
    {
        int tl1 = meio-ini+1, tl2=(fim-meio);
        int i,j,k;
        Arquivo_Java arq1 = new Arquivo_Java("Temp1.txt");
        Arquivo_Java arq2 = new Arquivo_Java("Temp2.txt");
        Registro auxi = new Registro();
        Registro auxj = new Registro();
        
        for (i = 0; i < tl1; i++) 
        {
            this.seekArq(ini+i);
            auxi.leDoArq(arquivo);
            arq1.seekArq(i);
            auxi.gravaNoArq(arq1.getFile());
            this.mov+=2;
        }
        
        for (i = 0; i < tl2; i++)
        {
            this.seekArq(meio+1+i);
            auxj.leDoArq(arquivo);
            arq2.seekArq(i);
            auxj.gravaNoArq(arq2.getFile());
            this.mov+=2;
        }
        
        i = 0;
        j = 0;
        k = ini;
        
        arq1.seekArq(0);
        auxi.leDoArq(arq1.getFile());
        arq2.seekArq(0);
        auxj.leDoArq(arq2.getFile());
        this.seekArq(ini);
        while (i < tl1 && j < tl2)  
        {  
            this.comp+=1;
            if (auxi.getNumero() <= auxj.getNumero())  
            {  
                auxi.gravaNoArq(arquivo);
                this.mov+=2;
                i++;
                arq1.seekArq(i);
                auxi.leDoArq(arq1.getFile());
            }  
            else  
            {  
                auxj.gravaNoArq(arquivo);
                this.mov+=2;
                j++;
                arq2.seekArq(j);
                auxj.leDoArq(arq2.getFile());
            }  
            k++;  
            this.seekArq(k);
        }  
        while (i < tl1)  
        {  
            arq1.seekArq(i);
            auxi.leDoArq(arq1.getFile());
            this.seekArq(k);
            auxi.gravaNoArq(arquivo);
            this.mov+=2;
            i++; 
            k++;  
            
        }  
        while (j < tl2)  
        {  
            arq2.seekArq(j);
            auxj.leDoArq(arq2.getFile());
            this.seekArq(k);
            auxj.gravaNoArq(arquivo);
            this.mov+=2;
            j++;   
            k++;  
            
        }
        arq1.truncate(0);
        arq2.truncate(0);
    }
    
}
