
package classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import pf.pilha;

/**
 *
 * @author Matheus
 */
public class Lista
{
    private No ini,fim;

    public Lista() 
    {
        this.ini = null;
        this.fim=null;
    }
    
    public void inicializa()
    {
        this.ini = null;
        this.fim=null;
    }

    public No getIni() {
        return ini;
    }

    public void setIni(No ini) {
        this.ini = ini;
    }
    
    public int getQtd()
    {
        No aux=ini;
        int i=0;
        while(aux!=null)
        {
            i++;
            aux=aux.getProx();
        }
        return i;
    }
    
    public void inserirL(int info)
    {
        No nova = new No(info);
        if(ini == null)
            ini = fim = nova;
        else
        {
            ini.setAnt(nova);
            nova.setProx(ini);
            ini = nova;
        }
    }
    
    public No buscaE(int info)
    {
        No aux=ini;
        while(aux!=null && info != aux.getInfo())
            aux = aux.getProx();
        return aux;
    }
    
    public No AndaNo(No aux,int qtd)
    {
        for(int i=0; aux!=null &&  i<qtd; i++)
            aux = aux.getProx();
        
        return aux;
    }
    
    public No VoltaNo(No aux,int qtd)
    {
        for(int i=0; i<qtd && aux!=null; i++)
            aux = aux.getAnt();
        
        return aux;
    }
    
    public No getPos(int pos)
    {
        No aux=this.ini;
        for(int i=0;i<pos;i++)
            aux=aux.getProx();
        return aux;
    }
    
    public int buscaB(No info,int TL)
    {
        int inicio=0, f = TL-1, meio=f/2;
        while(inicio < f && info.getInfo() != getPos(meio).getInfo())
        {
            if(info.getInfo() < getPos(meio).getInfo())
                f = meio -1;
            else
               inicio = meio +1;
            meio = (inicio +f)/2;
        }
        if(info.getInfo() > getPos(meio).getInfo())
            return meio+1;
        return meio;
    }
    
    public void criaL()
    {
        Random rand = new Random();
        int i=0,info,tamanho=0;
        while(tamanho<10)
            tamanho = rand.nextInt(25)+1;
        while(i<tamanho)
        {
            info = rand.nextInt(1000)+1;
            if(buscaE(info)==null)
                inserirL(info);
            i++;
        }
    }
    
    public void criaLista_potencia2()
    {
        Random rand = new Random();
        int i=0,info,tamanho = 8;
       while(i<tamanho)
       {
           info = rand.nextInt(1000)+1;
           if(buscaE(info)==null)
               inserirL(info);
           i++;
       }
    }
    
    public void exibeL()
    {
        No aux = ini;
        while(aux!=null)
        {
            System.out.println("Info: "+aux.getInfo());
            aux=aux.getProx();
        }
    }
    //-------------------------------------------- INSERÇÃO DIRETA --------------------------------------------------------
    public void insercaoDireta()
    {
        No auxVai, auxVolta;
        int aux;
        auxVai = this.ini.getProx();
        
        while(auxVai != null)
        {
            auxVolta = auxVai.getAnt();
            while(auxVolta != null && auxVolta.getInfo() > auxVolta.getProx().getInfo())
            {
                aux = auxVolta.getInfo();
                auxVolta.setInfo(auxVolta.getProx().getInfo());
                auxVolta.getProx().setInfo(aux);
                auxVolta = auxVolta.getAnt();
            }
            auxVai = auxVai.getProx();
        }
    }
    //-------------------------------------------- INSERÇÃO BINÁRIA --------------------------------------------------------
    public void insercaoBinaria()
    {
        int pos,i,j,auxI;
        No aux;
        for(i=1; i<getQtd();i++)
        {
            aux = getPos(i);
            auxI = aux.getInfo();
            pos = buscaB(aux,i);
            for(j=i; j>pos;j--)
                getPos(j).setInfo(getPos(j-1).getInfo());
            getPos(pos).setInfo(auxI);
        }
    }
    //-------------------------------------------- SELEÇÃO DIRETA --------------------------------------------------------
    public void selecaoDireta()
    {
        No inicio = ini, aux, menor;
        int min;
        while(inicio.getProx()!=null)
        {
            menor = inicio;
            min = menor.getInfo();
            aux = menor.getProx();
            while( aux != null)
            {
                if(aux.getInfo()<min)
                {
                    menor = aux;
                    min = menor.getInfo();
                }
                aux = aux.getProx();
            }
            menor.setInfo(inicio.getInfo());
            inicio.setInfo(min);
            inicio = inicio.getProx();
        }
    }
    //-------------------------------------------- BUBBLE SORT --------------------------------------------------------
    public void bubbleSort()
    {
        No vem=fim,vai;
        int aux;
        while(vem!=ini)
        {
           vai = ini;
           while(vai != vem)
           {
               if(vai.getInfo()> vai.getProx().getInfo())
               {
                   aux = vai.getInfo();
                   vai.setInfo(vai.getProx().getInfo());
                   vai.getProx().setInfo(aux);
               }
               vai = vai.getProx();
           }
           vem = vem.getAnt();
        }
    }
    //-------------------------------------------- SHAKE --------------------------------------------------------
    public void shake()
    {
        int aux, inicio=0, fim=getQtd()-1,i;
        No p = this.ini;
        while(inicio<fim)
        {
            for(i = inicio; i<fim; i++)
            {
                if(p.getInfo()>p.getProx().getInfo())
                {
                    aux = p.getInfo();
                    p.setInfo(p.getProx().getInfo());
                    p.getProx().setInfo(aux);
                }
                p = p.getProx();
            }
                
            fim--;
            p = p.getAnt();
            for(i = fim; i>inicio; i--)
            {
                if(p.getInfo()<p.getAnt().getInfo())
                {
                    aux = p.getInfo();
                    p.setInfo(p.getAnt().getInfo());
                    p.getAnt().setInfo(aux);
                }
                p = p.getAnt();
            }
            inicio++;
            p = p.getProx();
        }
    }
    //-------------------------------------------- HEAP --------------------------------------------------------
    public void heapSort()
    {
        int pai, filhoE, filhoD, maiorF, aux, TL=getQtd();
        while(TL>1)
        {
            for(pai = TL/2-1; pai>=0; pai--)
            {
                filhoE = 2*pai+1;
                filhoD = filhoE+1;
                maiorF = filhoE;
                if(filhoD < TL && getPos(filhoD).getInfo() > getPos(filhoE).getInfo())
                    maiorF = filhoD;
                if(getPos(maiorF).getInfo() > getPos(pai).getInfo())
                {
                    aux = getPos(pai).getInfo();
                    getPos(pai).setInfo(getPos(maiorF).getInfo());
                    getPos(maiorF).setInfo(aux);
                }
            }
            aux = ini.getInfo();
            ini.setInfo(getPos(TL-1).getInfo());
            getPos(TL-1).setInfo(aux);
            TL--;
        }
    }
    //-------------------------------------------- SHELL --------------------------------------------------------
    public void shellSort()
    {
        int dist=4,aux,i,j,k;
        while(dist>0)
        {
            for(i=0; i < dist; i++)
            {
                for(j=i; j+dist < getQtd(); j+=dist)
                {
                    if(getPos(j).getInfo() > getPos(j+dist).getInfo())
                    {
                        aux = getPos(j).getInfo();
                        getPos(j).setInfo(getPos(j+dist).getInfo());
                        getPos(j+dist).setInfo(aux);
                        for(k=j; k-dist>=i && getPos(k).getInfo() < getPos(k-dist).getInfo(); k-=dist)
                        {
                            aux = getPos(k).getInfo();
                            getPos(k).setInfo(getPos(k-dist).getInfo());
                            getPos(k-dist).setInfo(aux);
                        }
                    }
                }
            }
            dist/=2;
        }
    }
    //-------------------------------------------- QUICK SEM PIVO --------------------------------------------------------
    public void quick_sem_pivo()
    {
        QSP(0,getQtd()-1);
    }
    
    public void QSP(int inicio,int f)
    {
        int aux, i=inicio , j=f;
        boolean status = true;
        while(i<j)
        {
            if(status)
                while(i<j && getPos(i).getInfo() <= getPos(j).getInfo())
                    i++;
            else
                while(i<j && getPos(j).getInfo() >= getPos(i).getInfo())
                    j--;
            aux = getPos(i).getInfo();
            getPos(i).setInfo(getPos(j).getInfo());
            getPos(j).setInfo(aux);
            status = !status;
        }
        if(inicio < i-1)
            QSP(inicio,i-1);
        if(j+1 < f)
            QSP(j+1,f);
    }
    //-------------------------------------------- QUICK COM PIVO --------------------------------------------------------
    public void quick_com_pivo()
    {
        QCP(0,getQtd()-1);
    }
    
    public void QCP(int inicio, int f)
    {
        int i = inicio, j = f, pivo = getPos((inicio+f)/2).getInfo(),aux;
        while(i<j)
        {
            while(getPos(i).getInfo() < pivo)
                i++;
            while(getPos(j).getInfo() > pivo)
                j--;
            if(i<=j)
            {
                aux = getPos(i).getInfo();
                getPos(i).setInfo(getPos(j).getInfo());
                getPos(j).setInfo(aux);
                i++;
                j--;
            }
        }
        if(inicio<j)
            QCP(inicio,j);
        if(i<f)
            QCP(i,f);
    }
    //-------------------------------------------- MERGE 1 IMPLEMENTAÇÃO --------------------------------------------------------
    public void mergeSort()
    {
        Lista aux1 = new Lista();
        Lista aux2 = new Lista();
        int seq =1;
        while(seq < getQtd())
        {
            particaoM(aux1,aux2);
            fusaoM(aux1,aux2,seq);
            seq = seq*2;
            aux1.inicializa();
            aux2.inicializa();
        }
    }
    
    public void particaoM(Lista aux1,Lista aux2)
    {
        int tamanho = getQtd()/2,i=0;
        No no1=ini,no2=getPos(tamanho);
        for(i=0; i<tamanho; i++)
        {
            aux1.inserirFinal(no1.getInfo());
            aux2.inserirFinal(no2.getInfo());
            no1 = no1.getProx();
            no2 = no2.getProx();
        }
    }
    
    public void inserirFinal(int info)
    {
        No no = new No(info);
        if(ini == null)
            ini = fim = no;
        else
        {
            no.setAnt(fim);
            fim.setProx(no);
            fim = no;
        }   
    }
    
   public void fusaoM(Lista aux1, Lista aux2, int seq)
    {
        int i = 0, j = 0, k = 0, aux_s = seq;
        No noI=aux1.getPos(0), noJ=aux2.getPos(0), inicio = ini;
        while(inicio!=null)
        {
            while(i<seq && j<seq)
            {
                if(noI.getInfo() < noJ.getInfo())
                {
                    inicio.setInfo(noI.getInfo());
                    inicio = inicio.getProx();
                    noI = noI.getProx();
                    i++;
                }    
                else
                {    
                    inicio.setInfo(noJ.getInfo());
                    inicio = inicio.getProx();
                    noJ = noJ.getProx();
                    j++;
                }
            }
            while(i<seq)
            {
                inicio.setInfo(noI.getInfo());
                inicio = inicio.getProx();
                noI = noI.getProx();
                i++;
            }
            while(j<seq)
            {
                inicio.setInfo(noJ.getInfo());
                inicio = inicio.getProx();
                noJ = noJ.getProx();
                j++;
            }
            seq = seq + aux_s;
        }
    }
   // ----------------------------------- MERGE 2 IMPLEMENTAÇÃO ---------------------------------------------------------------
    public void fusao2(Lista aux,int inicio1, int f1, int inicio2, int f2)
    {
        int i = inicio1, j = inicio2;
        aux.inicializa();
        while(i <= f1 && j<= f2)
            if(getPos(i).getInfo() < getPos(j).getInfo())
                aux.inserirFinal(getPos(i++).getInfo());
            else
                aux.inserirFinal(getPos(j++).getInfo());
        while(i<=f1)
            aux.inserirFinal(getPos(i++).getInfo());
        while(j<=f2)
            aux.inserirFinal(getPos(j++).getInfo());
        for(i=0;i<aux.getQtd();i++)
            getPos(i+inicio1).setInfo(aux.getPos(i).getInfo());
    }
    
    public void particao2(Lista aux,int esq,int dir)
    {
        int meio;
        if(esq<dir)
        {
            meio = (esq+dir)/2;
            particao2(aux,esq,meio);
            particao2(aux,meio+1,dir);
            fusao2(aux,esq,meio,meio+1,dir);
        }
    }
    
    public void mergeSort2()
    {
        Lista aux = new Lista();
        particao2(aux,0,getQtd()-1);
    }
//------------------------------------------ MERGE ITERATIVO --------------------------------------------------------------------
    public void mergeI()
    {
	Lista aux = new Lista();
        particaoI(0, getQtd()-1, aux);
    }   
    

    public void particaoI(int esq, int dir, Lista aux)
    {
	pilha P1, P2;
        P1 = new pilha();
        P2 = new pilha();
	int meio = (esq+dir)/2,ini,fim;
	P1.init();
	P2.init();
	P1.push(esq);
	P1.push(dir);
	while(!P1.isEmpty())
	{
            fim = P1.pop();
            ini = P1.pop();
	    if(ini<fim)
     	    {
		P2.push(ini);
		P2.push(fim);
            	meio = (ini+fim)/2;
		P1.push(ini);
		P1.push(meio);
		P1.push(meio+1);
		P1.push(fim);
            }
	}

        while(!P2.isEmpty())
        {
	     fim = P2.pop();
	     ini = P2.pop();	 
	     meio = (ini+fim)/2;	
             fusao2(aux,ini,meio,meio+1,fim);
        }  
    }
    //-------------------------------------------- QUICK SEM PIVO ITERATIVO -------------------------------------------------------------------
    public void quickI()
    {
        QSPI(0,getQtd()-1);
    }
    
    public void QSPI(int inicio,int f)
    {
        int aux, i=inicio , j=f;
        boolean status = true;
        pilha P1 = new pilha();
        P1.init();
        P1.push(inicio);
        P1.push(f);
        while(!P1.isEmpty())
        {
            f = P1.pop();
            inicio = P1.pop();
            i=inicio;
            j = f;
            while(i<j)
            {
                if(status)
                    while(i<j && getPos(i).getInfo() <= getPos(j).getInfo())
                        i++;
                else
                    while(i<j && getPos(j).getInfo() >= getPos(i).getInfo())
                        j--;
                aux = getPos(i).getInfo();
                getPos(i).setInfo(getPos(j).getInfo());
                getPos(j).setInfo(aux);
                status = !status;
            }
            if(inicio < i-1)
            {
                P1.push(inicio);
                P1.push(i-1);
            }
            if(j+1 < f)
            {
                P1.push(j+1);
                P1.push(f);

            }
        }
    }
    //-------------------------------------------- QUICK COM PIVO ITERATIVO -------------------------------------------------------------------
    public void quickCI()
    {
        QCPI(0,getQtd()-1);
    }
    
    public void QCPI(int inicio,int f)
    {
        int aux, i=inicio , j=f,pivo;
        pilha P1 = new pilha();
        P1.init();
        P1.push(inicio);
        P1.push(f);
        while(!P1.isEmpty())
        {
            f = P1.pop();
            inicio = P1.pop();
            i=inicio;
            j = f;
            pivo = getPos((inicio+f)/2).getInfo();
            while(i<j)
            {
                while(getPos(i).getInfo() < pivo)
                    i++;
                while(getPos(j).getInfo() > pivo)
                    j--;
                if(i<=j)
                {
                    aux = getPos(i).getInfo();
                    getPos(i).setInfo(getPos(j).getInfo());
                    getPos(j).setInfo(aux);
                    i++;
                    j--;
                }
            }
            if(inicio < j)
            {
                P1.push(inicio);
                P1.push(j);
            }
            if(i < f)
            {
                P1.push(i);
                P1.push(f);
            }
        }
    }
    //--------------------------------------------- METODOS PESQUISADOS ---------------------------------------------------------------------------
    
        public void novaListaCounting(int tam)
    {
        ini = fim = null;
        int num;
        Random r = new Random();
        No aux = null;
        for(int i=0; i<tam; i++)
        {
            num = 0;
            No novo = new No(num);
            if(i==0)
            {
                ini = novo;
                aux = ini;
            }
            else
            {
                novo.setAnt(aux);
                aux.setProx(novo);
                aux = novo;
                if(i==tam-1)
                    fim = aux;
            } 
        }
    }
    
    public void Counting()
    {
        Lista aux = new Lista();
        No auxNo = this.ini;
        No Nova = new No();
        int maior = 0;
        for(int i=0;i<getQtd();i++)
        {
            if(auxNo.getInfo()>maior)
                maior = auxNo.getInfo();
            auxNo = auxNo.getProx();
        }
        aux.novaListaCounting(maior+1);

        auxNo = this.ini;
        for(int i=0;i<getQtd();i++)
        {
            Nova = aux.ini;
            Nova = this.AndaNo(Nova, auxNo.getInfo());
            Nova.setInfo(Nova.getInfo()+1);
            auxNo = auxNo.getProx();
        }
        auxNo = ini;
        Nova = aux.ini;
        int j = 0;
        for (int i = 0; i < getQtd(); i++) {
            while (Nova != null && Nova.getInfo() == 0) {
                Nova = Nova.getProx();
                j++;
            }
            if (Nova != null) {
                Nova.setInfo(Nova.getInfo() - 1);
            }
            auxNo.setInfo(j);
            auxNo = auxNo.getProx();
        }
        aux.inicializa();

    }
    
    public void Bucket()
    {
        Lista lis1 = new Lista();
        Lista lis2 = new Lista();
        Lista lis3 = new Lista();
        Lista lis4 = new Lista();
        Lista lis5 = new Lista();
        int i=0;
        No aux = this.ini;
        while(aux!=null)
        {
            if (aux.getInfo() < 50) {
                lis1.inserirFinal(aux.getInfo());
            } else if (aux.getInfo() < 100) {
                lis2.inserirFinal(aux.getInfo());
            } else if (aux.getInfo() < 150) {
                lis3.inserirFinal(aux.getInfo());
            } else if (aux.getInfo() < 200) {
                lis4.inserirFinal(aux.getInfo());
            } else {
                lis5.inserirFinal(aux.getInfo());
            }
            
            aux = aux.getProx();
        }
        
        lis1.insertionSortBucket();
        lis2.insertionSortBucket();
        lis3.insertionSortBucket();
        lis4.insertionSortBucket();
        lis5.insertionSortBucket();
        
        
        //junção
        int TL=0;
        this.fusaoBucket(0, lis1);
        TL += lis1.getQtd();
        this.fusaoBucket(TL, lis2);
        TL += lis2.getQtd();
        this.fusaoBucket(TL, lis3);
        TL += lis3.getQtd();
        this.fusaoBucket(TL, lis4);
        TL += lis4.getQtd();
        this.fusaoBucket(TL, lis5);
        TL += lis5.getQtd();
        
    }
    
    public void insertionSortBucket()
    {
        int i=1, temp;
        No p = ini,aux;
        while(i<getQtd())
        {
            p = p.getProx();
            temp = p.getInfo();
            aux = getPos(i);
            while(aux.getAnt()!=null && temp<aux.getAnt().getInfo())
            {
                aux.setInfo(aux.getAnt().getInfo());
                aux = aux.getAnt();
                aux.setInfo(temp);
            }
            i++;
        }
    }
    
    public void fusaoBucket(int pos, Lista aux)
    {
        No p = getPos(pos);
        No pAux = aux.getIni();
        while(pAux != null)
        {
            p.setInfo(pAux.getInfo());
            pAux = pAux.getProx();
            p = p.getProx();
        }
    }
    
    private void CountingRadix(int exp)
    {
        int Counting [] = new int [10];
        int vet_aux[] = new int[getQtd()];
        No aux;
        for(int i=0; i<10; i++)
            Counting[i] = 0;
        
        for(int i=0; i<getQtd(); i++)
        {
            aux = getPos(i);
            Counting[(aux.getInfo()/exp)%10]++;
        }
        
        for (int i = 1; i < 10; i++)
            Counting[i] += Counting[i - 1];
        
        
        for (int i = getQtd()-1; i >= 0; i--) 
        {
            aux = getPos(i);
            vet_aux[Counting[(aux.getInfo()/exp)%10] - 1] = aux.getInfo();
            Counting[(aux.getInfo()/exp) % 10]--;
        }
        
        for (int i = 0; i < getQtd(); i++)
        {
            aux = getPos(i);
            aux.setInfo(vet_aux[i]);
        }
    }
    
    public void Radix()
    {
        int maior = 0;
        No aux =ini;
        while(aux!=null)
        {
            if(aux.getInfo()>maior)
                maior = aux.getInfo();
            aux = aux.getProx();
        }
        
        for(int exp = 1; maior / exp > 0; exp *= 10)
            this.CountingRadix(exp);
    }
    
    public void Comb()
    {
        this.Classificar();
    }
    
    public void Classificar()
    {
        int intervalo = (int) (getQtd()/1.3);
        int i = 0, temp;
        No aux;
        No inter;
        while(intervalo > 0 && i != getQtd()-1)
        {
            i = 0;
            while((i+intervalo) < getQtd())
            {
                aux = getPos(i);
                inter = getPos(i+intervalo);
                if(aux.getInfo() > inter.getInfo())
                {
                    temp = aux.getInfo();
                    aux.setInfo(inter.getInfo());
                    inter.setInfo(temp);
                }
                i++;
            }
            intervalo = (int) (intervalo / 1.3);
        }
    }
    
    public void Gnome()
    {
        int i = 1,temp;
        No aux,ant;
        while(i<getQtd())
        {
            if(i==0)
                i++;
            aux = getPos(i);
            ant = getPos(i-1);
            if(aux.getInfo()>=ant.getInfo())
                i++;
            else
            {
                temp = aux.getInfo();
                aux.setInfo(ant.getInfo());
                ant.setInfo(temp);
                i--;
            }
        }

    }
    
    public void Tim()
    {
        int i, tam,TL = this.getQtd(), bloco=32, ini, meio, fim;  
        
        for (i = 0; i < TL; i+=bloco)  
            this.insertionSortTim(i, menor((i+bloco-1),(TL-1)));  
        
        for (tam = bloco; tam < TL; tam = 2*tam){  
            for (ini = 0; ini < TL; ini += 2*tam){  
                meio = ini + tam - 1;  
                fim = menor((ini + 2*tam - 1), (TL-1)); 
                
                if(meio < fim)
                    juncao(ini, meio, fim);  
            }  
        }
    }
    
    public void insertionSortTim(int ini, int fim)
    {
        int i=ini+1, temp, pos;
        No p = getPos(ini),aux;
        while(i<=fim)
        {
            p = p.getProx();
            pos = i;
            temp = p.getInfo();
            aux = getPos(pos);
            while(pos>ini && temp<aux.getAnt().getInfo())
            {
                aux.setInfo(aux.getAnt().getInfo());
                aux = aux.getAnt();
                pos--;
            }
            i++;
            aux.setInfo(temp);
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
        int tl1=meio-ini+1, tl2=(fim-meio);
        Lista lis1 = new Lista();
        Lista lis2 = new Lista();
        int i, j, k;
        No auxi, auxj;
        auxi = getPos(ini);
        for (i = 0; i < tl1; i++) 
        {
            lis1.inserirFinal(auxi.getInfo());
            auxi = auxi.getProx();
        }
        auxj = getPos(meio+1);
        for (i = 0; i < tl2; i++)
        {
            lis2.inserirFinal(auxj.getInfo());
            auxj = auxj.getProx();
        }
        

        i = 0;
        j = 0;
        k = ini;

        auxi = lis1.getPos(0);
        auxj = lis2.getPos(0);
        No auxk = getPos(ini);
        while (i < tl1 && j < tl2)  
        {  
            if (auxi.getInfo() <= auxj.getInfo())  
            {  
                auxk.setInfo(auxi.getInfo());  
                i++;
                auxi = auxi.getProx();
            }  
            else  
            {  
                auxk.setInfo(auxj.getInfo());  
                j++;
                auxj = auxj.getProx();
            }  
            k++;  
            auxk = auxk.getProx();
        }  
        while (i < tl1)  
        {  
            auxk.setInfo(auxi.getInfo());  
            i++;
            auxi = auxi.getProx();  
            k++;  
            auxk = auxk.getProx();  
        }  

        while (j < tl2)  
        {  
            auxk.setInfo(auxj.getInfo());  
            j++;
            auxj = auxj.getProx();  
            k++;  
            auxk = auxk.getProx();  
        }  
    }

}

