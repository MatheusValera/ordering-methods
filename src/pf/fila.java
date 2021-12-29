
package pf;

import classes.No;

/**
 *
 * @author Matheus
 */
public class fila 
{
    private No inicio,fim;

    public fila() 
    {
        inicio=fim=null;
    }
    
    public void init()
    {
        inicio=fim=null;
    }
    
    public void enqueue(int info)
    {
        No novo = new No(info);
        if(inicio==null)
            inicio=fim=novo;
        else
            fim.setProx(novo);
    }
    
    public int dequeue()
    {
        No aux = inicio;
        inicio = inicio.getProx();
        return aux.getInfo();
    }
    
    public boolean isEmpty()
    {
        boolean status=false;
        if(inicio==null)
            status=true;
        return status;
    }
    
    
}
