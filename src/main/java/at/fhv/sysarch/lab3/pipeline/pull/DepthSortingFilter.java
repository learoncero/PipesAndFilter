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

public class DepthSortingFilter extends APullFilter<Face, Face>{
    @Override
    public Optional<Face> read() {
        return Optional.empty();
    }

    @Override
    public Face process(Face data) {
        return null;
    }
}
