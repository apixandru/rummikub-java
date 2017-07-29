package com.apixandru.rummikub.swing.local;

import com.apixandru.rummikub.swing.shared.GameFrame;

/**
 * @author Alexandru-Constantin Bledea
 * @since Jul 29, 2017
 */
public class LocalMain {

    public static void main(String[] args) {
        LocalGameConfigurer skippy = new LocalGameConfigurer("skippy");
        GameFrame.run("skippy", skippy);
    }

}
