/*
 * Copyright (c) 2024 Sarah N
 *
 * Project Name:         cgpipeline
 * Description:
 *
 * Date of Creation/
 * Last Update:          28/05/2024
 */

package at.fhv.sysarch.lab3.pipeline.pull;

import at.fhv.sysarch.lab3.obj.Face;
import java.util.Optional;
import java.util.PriorityQueue;

public class PullDepthSortingFilter implements IPullFilter<Face, Face> {
    private IPullPipe<Face> predecessor;
    private PriorityQueue<Face> faces;

    @Override
    public void setPipePredecessor(IPullPipe<Face> pipePredecessor) {
        this.predecessor = pipePredecessor;
        faces = new PriorityQueue<>((face1, face2) -> {

            float face1_z = (face1.getV1().getZ() + face1.getV2().getZ() + face1.getV3().getZ()) / 3;
            float face2_z = (face2.getV1().getZ() + face2.getV2().getZ() + face2.getV3().getZ()) / 3;

            return Float.compare(face1_z, face2_z);
        });
    }

    @Override
    public Optional<Face> read() {
        Optional <Face> optionalFace = predecessor.read();
        while (optionalFace.isPresent()) {
            faces.add(optionalFace.get());
            optionalFace = predecessor.read();
        }
        return Optional.ofNullable(faces.poll());

    }

    @Override
    public Face process(Face data) {
        return null;
    }
}
