/*
 * Copyright (c) 2013, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

// diffuse fragment shader

#ifdef GL_ES

#extension GL_OES_standard_derivatives : enable

// Define default float precision for fragment shaders
#ifdef GL_FRAGMENT_PRECISION_HIGH
precision highp float;
precision highp int;
#else
precision mediump float;
precision mediump int;
#endif

#else

// Ignore GL_ES precision specifiers:
#define lowp
#define mediump
#define highp

#endif

uniform vec4 diffuseColor;
uniform sampler2D diffuseTexture;

varying vec2 oTexCoords;

vec4 apply_diffuse() {
    return vec4(0);
}

