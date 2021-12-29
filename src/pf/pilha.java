
package pf;

import classes.No;

/**
 *
 * @author Matheus
 */
public class pilha 
{
    private No inicio;

    public pilha() 
    {
       this.inicio = null;
    }
    
    public void init()
    {
       this.inicio = null;
    }
    
    public void push(int info)
    {
        No novo = new No(info);
        if(inicio==null)
            inicio=novo;
        else
        {
            novo.setProx(inicio);
            inicio=novo;
        }
    }
    
    public int pop()
    {
        No aux = inicio;
        inicio = aux.getProx();
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
