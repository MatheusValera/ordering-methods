
package classes;

/**
 *
 * @author Matheus
 */
public class No 
{
    private int info;
    private No prox,ant;

    public No() {
    }

    
    public No(int info) 
    {
        this.info = info;
        this.prox=null;
        this.ant=null;
    }

    public int getInfo() {
        return info;
    }

    public void setInfo(int info) {
        if(info>0)
            this.info = info;
    }

    public No getProx() {
        return prox;
    }

    public void setProx(No prox) {
        this.prox = prox;
    }

    public No getAnt() {
        return ant;
    }

    public void setAnt(No ant) {
        this.ant = ant;
    }
}
