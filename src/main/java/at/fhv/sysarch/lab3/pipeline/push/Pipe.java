package at.fhv.sysarch.lab3.pipeline.push;

public class Pipe<I> implements IPushPipe<I> {

    private IPushFilter<I, ?> successor;

    @Override
    public void setFilterSuccessor(IPushFilter<I, ?> filterSuccessor) {
        this.successor = filterSuccessor;
    }

    @Override
    public void write(I input) {
        this.successor.write(input);
    }

}
