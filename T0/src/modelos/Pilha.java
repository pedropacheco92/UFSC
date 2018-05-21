package modelos;

import java.util.ArrayList;
import java.util.List;

public class Pilha<T> {

    private List<T> pilha = new ArrayList<>();
    private int prox = 0;

    public void push(T x) {
        this.pilha.add(this.prox++, x);

    }

    public T pop() {
        return this.pilha.remove(--this.prox);
    }

    public T top() {
        return this.pilha.get(this.prox - 1);

    }

    public boolean empty() {
        return this.prox == 0;
    }

    public boolean removeElement(T x) {
        this.prox--;// cf. aula
        return this.pilha.remove(x);
    }

}//
