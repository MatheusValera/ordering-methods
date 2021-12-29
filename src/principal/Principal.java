package principal;

import classes.Arquivo_Java;
import classes.Lista;
import java.io.IOException;

/**
 *
 * @author Matheus
 */
public class Principal {

    private static int reg=512;
    private Arquivo_Java arqOrd, arqRev, arqAlea, auxrev, auxrand, tabela;
    private int tini, tend, mov, com;
    private Lista sortlist, reverselist, randomlist;

    private void inicializaF() {
        this.arqOrd = new Arquivo_Java("Ordenado.dat");
        this.arqRev = new Arquivo_Java("Reverso.dat");
        this.arqAlea = new Arquivo_Java("Aleatorio.dat");
        this.tabela = new Arquivo_Java("Tabela512.txt");
        this.auxrand = new Arquivo_Java("auxiliar1.dat");
        this.auxrev = new Arquivo_Java("auxiliar2.dat");
    }

    private void cabecalho() throws IOException {
        tabela.getFile().writeBytes("|MÉTODOS DE ORDENAÇÃO|ARQUIVO ORDENADO\t\t\t\t|ARQUIVO EM ORDEM REVERSA\t\t "
                + "|ARQUIVO RANDÔMICO\n");
        tabela.getFile().writeBytes("|\t\t     |Comp. 1\t|Comp. 2|Mov. 1\t|Mov. 2\t|Tempo\t|Comp. 1|Comp. 2|Mov. 1\t|Mov. 2\t|Tempo\t|"
                + "Comp. 1|Comp. 2|Mov. 1\t|Mov. 2\t|Tempo\t|\n");
    }

    private void insereLinha(String name, int cp, double ce, int mp, double me, double time) throws IOException {
        tabela.getFile().writeBytes(name + " " + cp + "\t| " + (int) ce + "\t| " + mp + "\t| " + (int) me + "\t| " + (int) time + "\t|");
    }

    private void bubbleSort() throws IOException {

        arqOrd.initComp();
        arqOrd.initMov();
        tini = (int) System.currentTimeMillis();
        arqOrd.bubbleSort();

        tend = (int) System.currentTimeMillis();
        com = arqOrd.getComp();
        mov = arqOrd.getMov();
        insereLinha("|Bubble Sort\t     |", com, (Math.pow(reg, 2) - reg) / 2, mov, 0, tend - tini);

        auxrev.copiaArquivo(arqRev.getFile());
        auxrev.initComp();
        auxrev.initMov();

        tini = (int) System.currentTimeMillis();
        auxrev.bubbleSort();

        tend = (int) System.currentTimeMillis();
        com = auxrev.getComp();
        mov = auxrev.getMov();
        insereLinha("", com, (Math.pow(reg, 2) - reg) / 2, mov, Math.pow(reg, 2) / (4 + 3 * (reg - 1)), tend - tini);

        auxrand.copiaArquivo(arqAlea.getFile());
        auxrand.initComp();
        auxrand.initMov();
        tini = (int) System.currentTimeMillis();
        auxrand.bubbleSort();

        tend = (int) System.currentTimeMillis();
        com = auxrand.getComp();
        mov = auxrand.getMov();

        insereLinha("", com, (Math.pow(reg, 2) - reg) / 2, mov, reg * (Math.log((double) reg) + 0.577216f), tend - tini);
        tabela.getFile().writeBytes("\n");
    }

    private void shakeSort() throws IOException {
        arqOrd.initComp();
        arqOrd.initMov();
        tini = (int) System.currentTimeMillis();
        arqOrd.shakeSort();
        tend = (int) System.currentTimeMillis();
        com = arqOrd.getComp();
        mov = arqOrd.getMov();
        insereLinha("|Shake Sort\t     |", com, (Math.pow(reg, 2) - reg) / 2, mov, 0, tend - tini);

        auxrev.copiaArquivo(arqRev.getFile());
        auxrev.initComp();
        auxrev.initMov();
        tini = (int) System.currentTimeMillis();
        auxrev.shakeSort();

        tend = (int) System.currentTimeMillis();
        com = auxrev.getComp();

        mov = auxrev.getMov();
        insereLinha("", com, (Math.pow(reg, 2) - reg) / 2, mov, 3 * (Math.pow(reg, 2) - reg) / 4, tend - tini);

        auxrand.copiaArquivo(arqAlea.getFile());
        auxrand.initComp();
        auxrand.initMov();
        tini = (int) System.currentTimeMillis();
        auxrand.shakeSort();

        tend = (int) System.currentTimeMillis();
        com = auxrand.getComp();

        mov = auxrand.getMov();
        insereLinha("", com, (Math.pow(reg, 2) - reg) / 2, mov, 3 * (Math.pow(reg, 2) - reg) / 2, tend - tini);
        tabela.getFile().writeBytes("\n");
    }

    private void shellSort() throws IOException {
        arqOrd.initComp();
        arqOrd.initMov();
        tini = (int) System.currentTimeMillis();
        arqOrd.shellSort();

        tend = (int) System.currentTimeMillis();
        com = arqOrd.getComp();

        mov = arqOrd.getMov();
        insereLinha("|Shell Sort\t     |", com, -1, mov, -1, tend - tini);

        auxrev.copiaArquivo(arqRev.getFile());
        auxrev.initComp();
        auxrev.initMov();
        tini = (int) System.currentTimeMillis();
        auxrev.shellSort();

        tend = (int) System.currentTimeMillis();
        com = auxrev.getComp();
        mov = auxrev.getMov();
        insereLinha("", com, -1, mov, -1, tend - tini);

        auxrand.copiaArquivo(arqAlea.getFile());
        auxrand.initComp();
        auxrand.initMov();
        tini = (int) System.currentTimeMillis();
        auxrand.shellSort();

        tend = (int) System.currentTimeMillis();
        com = auxrand.getComp();
        mov = auxrand.getMov();
        insereLinha("", com, -1, mov, -1, tend - tini);
        tabela.getFile().writeBytes("\n");
    }

    private void heapSort() throws IOException {
        arqOrd.initComp();
        arqOrd.initMov();
        tini = (int) System.currentTimeMillis();
        arqOrd.heapSort();

        tend = (int) System.currentTimeMillis();
        com = arqOrd.getComp();
        mov = arqOrd.getMov();
        insereLinha("|Heap Sort\t     |", com, -1, mov, -1, tend - tini);

        auxrev.copiaArquivo(arqRev.getFile());
        auxrev.initComp();
        auxrev.initMov();
        tini = (int) System.currentTimeMillis();
        auxrev.heapSort();
        tend = (int) System.currentTimeMillis();
        com = auxrev.getComp();

        mov = auxrev.getMov();
        insereLinha("", com, -1, mov, -1, tend - tini);

        auxrand.copiaArquivo(arqAlea.getFile());
        auxrand.initComp();
        auxrand.initMov();
        tini = (int) System.currentTimeMillis();
        auxrand.heapSort();
        tend = (int) System.currentTimeMillis();
        com = auxrand.getComp();
        mov = auxrand.getMov();

        insereLinha("", com, -1, mov, -1, tend - tini);
        tabela.getFile().writeBytes("\n");
    }

    private void ordQuickSemPivo() throws IOException {
        arqOrd.initComp();
        arqOrd.initMov();
        tini = (int) System.currentTimeMillis();
        arqOrd.ordQuickSemPivo();
        tend = (int) System.currentTimeMillis();
        com = arqOrd.getComp();
        mov = arqOrd.getMov();
        insereLinha("|Quick Sort 1\t     |", com, -1, mov, -1, tend - tini);

        auxrev.copiaArquivo(arqRev.getFile());
        auxrev.initComp();
        auxrev.initMov();
        tini = (int) System.currentTimeMillis();
        auxrev.ordQuickSemPivo();
        tend = (int) System.currentTimeMillis();
        com = auxrev.getComp();
        mov = auxrev.getMov();

        insereLinha("", com, -1, mov, -1, tend - tini);

        auxrand.copiaArquivo(arqAlea.getFile());
        auxrand.initComp();
        auxrand.initMov();
        tini = (int) System.currentTimeMillis();
        auxrand.ordQuickSemPivo();
        tend = (int) System.currentTimeMillis();
        com = auxrand.getComp();
        mov = auxrand.getMov();
        insereLinha("", com, -1, mov, -1, tend - tini);
        tabela.getFile().writeBytes("\n");
    }

    private void ordQuickComPivo() throws IOException {
        arqOrd.initComp();
        arqOrd.initMov();
        tini = (int) System.currentTimeMillis();
        arqOrd.ordQuickComPivo();
        tend = (int) System.currentTimeMillis();
        com = arqOrd.getComp();

        mov = arqOrd.getMov();
        insereLinha("|Quick Sort 2\t     |", com, -1, mov, -1, tend - tini);

        auxrev.copiaArquivo(arqRev.getFile());
        auxrev.initComp();
        auxrev.initMov();
        tini = (int) System.currentTimeMillis();
        auxrev.ordQuickComPivo();
        tend = (int) System.currentTimeMillis();
        com = auxrev.getComp();

        mov = auxrev.getMov();
        insereLinha("", com, -1, mov, -1, tend - tini);

        auxrand.copiaArquivo(arqAlea.getFile());
        auxrand.initComp();
        auxrand.initMov();
        tini = (int) System.currentTimeMillis();
        auxrand.ordQuickComPivo();
        tend = (int) System.currentTimeMillis();
        com = auxrand.getComp();

        mov = auxrand.getMov();
        insereLinha("", com, (Math.pow(reg, 2) - reg) / 2, mov, reg * (Math.log((double) reg) + 0.577216f), tend - tini);
        tabela.getFile().writeBytes("\n");
    }

    private void MergeSort1() throws IOException {
        arqOrd.initComp();
        arqOrd.initMov();
        tini = (int) System.currentTimeMillis();
        arqOrd.merge1();
        tend = (int) System.currentTimeMillis();
        com = arqOrd.getComp();
        mov = arqOrd.getMov();
        insereLinha("|Merge 1\t     |", com, -1, mov, -1, tend - tini);

        auxrev.copiaArquivo(arqRev.getFile());
        auxrev.initComp();
        auxrev.initMov();
        tini = (int) System.currentTimeMillis();
        auxrev.merge1();

        tend = (int) System.currentTimeMillis();
        com = auxrev.getComp();
        mov = auxrev.getMov();
        insereLinha("", com, -1, mov, -1, tend - tini);

        auxrand.copiaArquivo(arqAlea.getFile());
        auxrand.initComp();
        auxrand.initMov();
        tini = (int) System.currentTimeMillis();
        auxrand.merge1();
        tend = (int) System.currentTimeMillis();
        com = auxrand.getComp();

        mov = auxrand.getMov();
        insereLinha("", com, -1, mov, -1, tend - tini);
        tabela.getFile().writeBytes("\n");
    }

    private void MergeSort2() throws IOException {
        arqOrd.initComp();
        arqOrd.initMov();
        tini = (int) System.currentTimeMillis();
        arqOrd.merge2();
        tend = (int) System.currentTimeMillis();
        com = arqOrd.getComp();
        mov = arqOrd.getMov();

        insereLinha("|Merge 2\t     |", com, -1, mov, -1, tend - tini);

        auxrev.copiaArquivo(arqRev.getFile());
        auxrev.initComp();
        auxrev.initMov();
        tini = (int) System.currentTimeMillis();
        auxrev.merge2();
        tend = (int) System.currentTimeMillis();
        com = auxrev.getComp();
        mov = auxrev.getMov();
        insereLinha("", com, -1, mov, -1, tend - tini);

        auxrand.copiaArquivo(arqAlea.getFile());
        auxrand.initComp();
        auxrand.initMov();

        tini = (int) System.currentTimeMillis();
        auxrand.merge2();
        tend = (int) System.currentTimeMillis();
        com = auxrand.getComp();
        mov = auxrand.getMov();
        insereLinha("", com, -1, mov, -1, tend - tini);
        tabela.getFile().writeBytes("\n");
    }

    private void countingSort() throws IOException {
        arqOrd.initComp();
        arqOrd.initMov();
        tini = (int) System.currentTimeMillis();
        arqOrd.countingSort();
        tend = (int) System.currentTimeMillis();
        com = arqOrd.getComp();

        mov = arqOrd.getMov();
        insereLinha("|countingSort\t     |", com, -1, mov, -1, tend - tini);

        auxrev.copiaArquivo(arqRev.getFile());
        auxrev.initComp();
        auxrev.initMov();
        tini = (int) System.currentTimeMillis();
        auxrev.countingSort();

        tend = (int) System.currentTimeMillis();
        com = auxrev.getComp();
        mov = auxrev.getMov();
        insereLinha("", com, -1, mov, -1, tend - tini);

        auxrand.copiaArquivo(arqAlea.getFile());
        auxrand.initComp();
        auxrand.initMov();
        tini = (int) System.currentTimeMillis();
        auxrand.countingSort();
        tend = (int) System.currentTimeMillis();
        com = auxrand.getComp();

        mov = auxrand.getMov();
        insereLinha("", com, -1, mov, -1, tend - tini);
        tabela.getFile().writeBytes("\n");
    }

    private void bucketSort() throws IOException {
        arqOrd.initComp();
        arqOrd.initMov();
        tini = (int) System.currentTimeMillis();
        arqOrd.bucketSort();
        tend = (int) System.currentTimeMillis();
        com = arqOrd.getComp();
        mov = arqOrd.getMov();
        insereLinha("|Gnome Sort\t     |", com, -1, mov, -1, tend - tini);

        auxrev.copiaArquivo(arqRev.getFile());
        auxrev.initComp();
        auxrev.initMov();
        tini = (int) System.currentTimeMillis();
        auxrev.bucketSort();
        tend = (int) System.currentTimeMillis();
        com = auxrev.getComp();

        mov = auxrev.getMov();
        insereLinha("", com, -1, mov, -1, tend - tini);

        auxrand.copiaArquivo(arqAlea.getFile());
        auxrand.initComp();
        auxrand.initMov();
        tini = (int) System.currentTimeMillis();
        auxrand.bucketSort();
        tend = (int) System.currentTimeMillis();
        com = auxrand.getComp();
        mov = auxrand.getMov();
        insereLinha("", com, -1, mov, -1, tend - tini);
        tabela.getFile().writeBytes("\n");
    }

    private void radixSort() throws IOException {
        arqOrd.initComp();
        arqOrd.initMov();
        tini = (int) System.currentTimeMillis();
        arqOrd.radixSort();
        tend = (int) System.currentTimeMillis();
        com = arqOrd.getComp();

        mov = arqOrd.getMov();
        insereLinha("|Radix\t\t     |", com, -1, mov, -1, tend - tini);

        auxrev.copiaArquivo(arqRev.getFile());
        auxrev.initComp();
        auxrev.initMov();
        tini = (int) System.currentTimeMillis();
        auxrev.radixSort();
        tend = (int) System.currentTimeMillis();
        com = auxrev.getComp();
        mov = auxrev.getMov();
        insereLinha("", com, -1, mov, -1, tend - tini);

        auxrand.copiaArquivo(arqAlea.getFile());
        auxrand.initComp();
        auxrand.initMov();
        tini = (int) System.currentTimeMillis();
        auxrand.radixSort();

        tend = (int) System.currentTimeMillis();
        com = auxrand.getComp();
        mov = auxrand.getMov();
        insereLinha("", com, -1, mov, -1, tend - tini);
        tabela.getFile().writeBytes("\n");
    }

    private void combSort() throws IOException {
        arqOrd.initComp();
        arqOrd.initMov();
        tini = (int) System.currentTimeMillis();
        arqOrd.combSort();
        tend = (int) System.currentTimeMillis();
        com = arqOrd.getComp();
        mov = arqOrd.getMov();
        insereLinha("|Comb\t\t     |", com, -1, mov, -1, tend - tini);

        auxrev.copiaArquivo(arqRev.getFile());
        auxrev.initComp();

        auxrev.initMov();
        tini = (int) System.currentTimeMillis();
        auxrev.combSort();
        tend = (int) System.currentTimeMillis();
        com = auxrev.getComp();
        mov = auxrev.getMov();
        insereLinha("", com, -1, mov, -1, tend - tini);

        auxrand.copiaArquivo(arqAlea.getFile());
        auxrand.initComp();
        auxrand.initMov();
        tini = (int) System.currentTimeMillis();
        auxrand.combSort();
        tend = (int) System.currentTimeMillis();
        com = auxrand.getComp();
        mov = auxrand.getMov();

        insereLinha("", com, -1, mov, -1, tend - tini);
        tabela.getFile().writeBytes("\n");
    }

    private void gnomeSort() throws IOException {
        arqOrd.initComp();
        arqOrd.initMov();
        tini = (int) System.currentTimeMillis();
        arqOrd.gnomeSort();
        tend = (int) System.currentTimeMillis();
        com = arqOrd.getComp();
        mov = arqOrd.getMov();
        insereLinha("|Gnome Sort\t     |", com, -1, mov, -1, tend - tini);

        auxrev.copiaArquivo(arqRev.getFile());
        auxrev.initComp();
        auxrev.initMov();
        tini = (int) System.currentTimeMillis();
        auxrev.gnomeSort();
        tend = (int) System.currentTimeMillis();
        com = auxrev.getComp();
        mov = auxrev.getMov();
        insereLinha("", com, -1, mov, -1, tend - tini);

        auxrand.copiaArquivo(arqAlea.getFile());
        auxrand.initComp();
        auxrand.initMov();
        tini = (int) System.currentTimeMillis();
        auxrand.gnomeSort();
        tend = (int) System.currentTimeMillis();
        com = auxrand.getComp();

        mov = auxrand.getMov();
        insereLinha("", com, -1, mov, -1, tend - tini);
        tabela.getFile().writeBytes("\n");
    }

    private void timSort() throws IOException {
        arqOrd.initComp();
        arqOrd.initMov();
        tini = (int) System.currentTimeMillis();
        arqOrd.timSort();
        tend = (int) System.currentTimeMillis();
        com = arqOrd.getComp();
        mov = arqOrd.getMov();
        insereLinha("|Tim Sort\t     |", com, -1, mov, -1, tend - tini);

        auxrev.copiaArquivo(arqRev.getFile());
        auxrev.initComp();
        auxrev.initMov();
        tini = (int) System.currentTimeMillis();
        auxrev.timSort();

        tend = (int) System.currentTimeMillis();
        com = auxrev.getComp();
        mov = auxrev.getMov();
        insereLinha("", com, -1, mov, -1, tend - tini);

        auxrand.copiaArquivo(arqAlea.getFile());
        auxrand.initComp();
        auxrand.initMov();
        tini = (int) System.currentTimeMillis();
        auxrand.timSort();
        tend = (int) System.currentTimeMillis();
        com = auxrand.getComp();
        mov = auxrand.getMov();
        insereLinha("", com, -1, mov, -1, tend - tini);
        tabela.getFile().writeBytes("\n");
    }

    private void insercaoDireta() throws IOException {
        arqOrd.initComp();
        arqOrd.initMov();
        tini = (int) System.currentTimeMillis();
        arqOrd.insercaoDireta();
        tend = (int) System.currentTimeMillis();
        com = arqOrd.getComp();

        mov = arqOrd.getMov();
        insereLinha("|Insertion Sort\t     |", com, reg - 1, mov, 3 * (reg - 1), tend - tini);

        auxrev.copiaArquivo(arqRev.getFile());
        auxrev.initComp();
        auxrev.initMov();
        tini = (int) System.currentTimeMillis();
        auxrev.insercaoDireta();
        tend = (int) System.currentTimeMillis();
        com = auxrev.getComp();
        mov = auxrev.getMov();
        insereLinha("", com, (Math.pow(reg, 2) + reg - 4) / 4, mov, (Math.pow(reg, 2) + (3 * reg) - 4) / 2, tend - tini);

        auxrand.copiaArquivo(arqAlea.getFile());
        auxrand.initComp();
        auxrand.initMov();

        tini = (int) System.currentTimeMillis();
        auxrand.insercaoDireta();
        tend = (int) System.currentTimeMillis();
        com = auxrand.getComp();
        mov = auxrand.getMov();
        insereLinha("", com, (Math.pow(reg, 2) + reg - 2) / 4, mov, (Math.pow(reg, 2) + (9 * reg) - 10) / 4, tend - tini);
        tabela.getFile().writeBytes("\n");
    }

    private void insercaoBinaria() throws IOException {
        arqOrd.initComp();
        arqOrd.initMov();
        tini = (int) System.currentTimeMillis();
        arqOrd.insercaoBinaria();
        tend = (int) System.currentTimeMillis();
        com = arqOrd.getComp();
        mov = arqOrd.getMov();
        insereLinha("|Binary Insertion    |", com, 0, mov, 3 * (reg - 1), tend - tini);

        auxrev.copiaArquivo(arqRev.getFile());
        auxrev.initComp();
        auxrev.initMov();
        tini = (int) System.currentTimeMillis();
        auxrev.insercaoBinaria();

        tend = (int) System.currentTimeMillis();
        com = auxrev.getComp();
        mov = auxrev.getMov();
        insereLinha("", com, (Math.pow(reg, 2) - reg) / 2, mov, (Math.pow(reg, 2) + 3 * reg - 4) / 2, tend - tini);

        auxrand.copiaArquivo(arqAlea.getFile());
        auxrand.initComp();
        auxrand.initMov();

        tini = (int) System.currentTimeMillis();
        auxrand.insercaoBinaria();
        tend = (int) System.currentTimeMillis();
        com = auxrand.getComp();
        mov = auxrand.getMov();
        insereLinha("", com, reg * (Math.log(reg)), mov, (Math.pow(reg, 2) + 9 * reg - 10) / 4, tend - tini);
        tabela.getFile().writeBytes("\n");
    }

    private void selecaoDireta() throws IOException {
        arqOrd.initComp();
        arqOrd.initMov();
        tini = (int) System.currentTimeMillis();
        arqOrd.selecaoDireta();
        tend = (int) System.currentTimeMillis();
        com = arqOrd.getComp();
        mov = arqOrd.getMov();
        insereLinha("|Select Sort\t     |", com, (Math.pow(reg, 2) - reg) / 2, mov, 3 * (reg - 1), tend - tini);

        auxrev.copiaArquivo(arqRev.getFile());
        auxrev.initComp();
        auxrev.initMov();
        tini = (int) System.currentTimeMillis();
        auxrev.selecaoDireta();
        tend = (int) System.currentTimeMillis();
        com = auxrev.getComp();
        mov = auxrev.getMov();
        insereLinha("", com, (Math.pow(reg, 2) - reg) / 2, mov, Math.pow(reg, 2) / 4 + (3 * (reg - 1)), tend - tini);

        auxrand.copiaArquivo(arqAlea.getFile());
        auxrand.initComp();
        auxrand.initMov();
        tini = (int) System.currentTimeMillis();
        auxrand.selecaoDireta();
        tend = (int) System.currentTimeMillis();
        com = auxrand.getComp();
        mov = auxrand.getMov();
        insereLinha("", com, (Math.pow(reg, 2) - reg) / 2, mov, reg * (Math.log((double) reg) + 0.577216f), tend - tini);
        tabela.getFile().writeBytes("\n");
    }


    public void geraTabela() throws IOException {
        inicializaF();
        cabecalho();
        arqOrd.criaOrdenado();
        arqRev.criaInverso();
        arqAlea.criaAleatorio();
        insercaoDireta();
        insercaoBinaria();
        selecaoDireta();
        bubbleSort();
        shakeSort();
        shellSort();
        heapSort();
        ordQuickSemPivo();
        ordQuickComPivo();
        MergeSort1();
        MergeSort2();
        countingSort();
        bucketSort();
        radixSort();
        combSort();
        gnomeSort();
        timSort();
    }
    
    public void geraLista()
    {
        Lista l = new Lista();
        System.out.println("INSERCAO DIRETA");
        l.criaL();
        l.insercaoDireta();
        l.exibeL();
        System.out.println("--------------------");
        System.out.println("INSERCAO BINARIA");
        l.inicializa();
        l.criaL();
        l.insercaoBinaria();
        l.exibeL();
        System.out.println("--------------------");
        System.out.println("SELECAO DIRETA");
        l.inicializa();
        l.criaL();
        l.selecaoDireta();
        l.exibeL();
        System.out.println("--------------------");
        System.out.println("BOLHA");
        l.inicializa();
        l.criaL();
        l.bubbleSort();
        l.exibeL();
        System.out.println("--------------------");
        System.out.println("SHAKE");
        l.inicializa();
        l.criaL();
        l.shake();
        l.exibeL();
        System.out.println("--------------------");
        System.out.println("SHELL SORT");
        l.inicializa();
        l.criaL();
        l.shellSort();
        l.exibeL();
        System.out.println("--------------------");
        System.out.println("HEAP SORT");
        l.inicializa();
        l.criaL();
        l.heapSort();
        l.exibeL();
        System.out.println("--------------------");
        System.out.println("QUICK SORT COM PIVO");
        l.inicializa();
        l.criaL();
        l.quick_com_pivo();
        l.exibeL();
        System.out.println("--------------------");
        System.out.println("QUICK SORT SEM PIVO");
        l.inicializa();
        l.criaL();
        l.quick_sem_pivo();
        l.exibeL();
        System.out.println("--------------------");
        System.out.println("MERGE SORT 1");
        l.inicializa();
        l.criaLista_potencia2();
        l.mergeSort();
        l.exibeL();
        System.out.println("--------------------");
        System.out.println("MERGE SORT 2");
        l.inicializa();
        l.criaLista_potencia2();
        l.mergeSort2();
        l.exibeL();
        System.out.println("--------------------");
        System.out.println("COUTING");
        l.inicializa();
        l.criaL();
        l.Counting();
        l.exibeL();
        System.out.println("--------------------");
        l.inicializa();
        System.out.println("BUCKET");
        l.criaL();
        l.Bucket();
        l.exibeL();
        System.out.println("--------------------");
        System.out.println("RADIX");
        l.inicializa();
        l.criaL();
        l.Radix();
        l.exibeL();
        System.out.println("--------------------");
        System.out.println("COMB");
        l.inicializa();
        l.criaL();
        l.Comb();
        l.exibeL();
        System.out.println("--------------------");
        System.out.println("GNOME");
        l.inicializa();
        l.criaL();
        l.Gnome();
        l.exibeL();
        System.out.println("--------------------");
        System.out.println("TIM");
        l.inicializa();
        l.criaL();
        l.Tim();
        l.exibeL();
    }

    public static void main(String[] args) throws IOException 
    {
    
        Principal trab = new Principal();
        System.out.println("---------------------------------- LISTA --------------------------------------------------------------");
        trab.geraLista();
        System.out.println("---------------------------------- ARQUIVO --------------------------------------------------------------");
        trab.geraTabela();
    }
}
