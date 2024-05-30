package at.fhv.sysarch.lab3.pipeline.push;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.obj.Model;

public class Source extends APushFilter<Model, Face> {

    @Override
    public void write(Model data) {
        data.getFaces().forEach(face -> successor.write(face));
    }

    @Override
    public Face process(Model data) {
        // no processing
        throw new UnsupportedOperationException("PushSource does not support process operation.");
    }
}
