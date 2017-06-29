package com.iflytek.facedemo.open;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import com.iflytek.facedemo.R;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.Random;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * This class implements our custom renderer. Note that the GL10 parameter passed in is unused for OpenGL ES 2.0
 * renderers -- the static class GLES20 is used instead.
 */
public class LessonOneRenderer3 implements GLSurfaceView.Renderer,Renderer {
    /**
     * Store the model matrix. This matrix is used to move models from object space (where each model can be thought
     * of being located at the center of the universe) to world space.



    /**
     * Store our model data in a float buffer.
     */
    private FloatBuffer mTriangle1Vertices;
    private FloatBuffer mTextBuffer;
    private ShortBuffer index;
    //
    private final FloatBuffer mCubeTextureCoordinates;
    private int mTextureDataHandle;

    /**
     * This will be used to pass in model position information.
     */
    private int mPositionHandle;


    /**
     * How many bytes per float.
     */
    private final int mBytesPerFloat = 4;


    private Context mActivityContext;


    private int mTextureUniformHandle;

    /**
     * This will be used to pass in model texture coordinate information.
     */
    private int mTextureCoordinateHandle;

    private float[] mModelMatrix = new float[16];

    /**
     * Store the view matrix. This can be thought of as our camera. This matrix transforms world space to eye space;
     * it positions things relative to our eye.
     */
    private float[] mViewMatrix = new float[16];

    /** Store the projection matrix. This is used to project the scene onto a 2D viewport. */
    private float[] mProjectionMatrix = new float[16];

    /** Allocate storage for the final combined matrix. This will be passed into the shader program. */
    private float[] mMVPMatrix = new float[16];

    //---

    private float[] quadVertex = new float[]{
            //左边
            -0.5f, 0.5f, 0.0f, // Position 0
            -0.5f, -0.5f, 0.0f, // Position 1
            -0.0f, -0.5f, 0.0f, // Position 2
            -0.0f, -0.5f, 0.0f, // Position 2
            0.0f, 0.5f, 0.0f, // Position 3
            -0.5f, 0.5f, 0.0f, // Position 0
            //右边
            0.0f, 0.5f, 0.0f, // Position 3
            -0.0f, -0.5f, 0.0f, // Position 2
            0.5f, -0.5f, 0.0f, // Position 5
            0.5f, -0.5f, 0.0f, // Position 5
            0.5f, 0.5f, 0.0f, // Position 4
            0.0f, 0.5f, 0.0f, // Position 3
    };
    private float[] textArr = new float[]{

            0f, 0.0f, // TexCoord 0
            0, 1.0f, // TexCoord 1
            0.5f, 1.0f, // TexCoord 2

            0.5f, 1.0f, // TexCoord 2
            0.5f, 0.0f, // TexCoord 3
            0f, 0.0f, // TexCoord 0
//
            0.5f, 0.0f, // TexCoord 3
            0.5f, 1.0f, // TexCoord 2
            1.0f, 1.0f, // TexCoord 5

           1.0f, 1.0f, // TexCoord 5
            1.0f, 0.0f, // TexCoord 4
            0.5f, 0.0f, // TexCoord 3
    };
    private short[] quadIndex = new short[]{
            (short) (0), // Position 0
            (short) (1), // Position 1
            (short) (2), // Position 2
            (short) (2), // Position 2
            (short) (3), // Position 3
            (short) (0), // Position 0
            (short) (3), // Position 0
            (short) (2), // Position 1
            (short) (5), // Position 2
            (short) (5), // Position 2
            (short) (4), // Position 3
            (short) (3), // Position 0
    };
    private int mProgramHandle;

    /**
     * Initialize the model data.
     */
    public LessonOneRenderer3(Context aContext) {
        mActivityContext = aContext.getApplicationContext();


        // This triangle is red, green, and blue.
        final float[] triangle1VerticesData = {
                // X, Y, Z,
                // R, G, B, A
                0.0f, 0.0f, 0.0f, // 左下角
                1.0f, 0.0f, 0.0f, // 右下角
                0.5f, 1.0f, 0.0f //
        };

        //
//        final float[] cubeTextureCoordinateData =
//                {
//                        0.0f, 0.0f, // 左下角
//                        1.0f, 0.0f, // 右下角
//                        0.5f, 1.0f //
//
//
//                };
        // Initialize the buffers.
        mTriangle1Vertices = ByteBuffer.allocateDirect(quadVertex.length * mBytesPerFloat)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        index = ByteBuffer.allocateDirect(quadIndex.length * mBytesPerFloat)
                .order(ByteOrder.nativeOrder()).asShortBuffer();
        mCubeTextureCoordinates = ByteBuffer.allocateDirect(textArr.length * mBytesPerFloat)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        mCubeTextureCoordinates.put(textArr).position(0);

        mTriangle1Vertices.put(quadVertex).position(0);
        index.put(quadIndex).position(0);


    }

    @Override
    public void onSurfaceCreated(GL10 glUnused, EGLConfig config) {
        // Set the background clear color to gray.
        GLES20.glClearColor(0.8f, 0.8f, 0.0f, 0.0f);
        GLES20.glEnable(GLES20.GL_TEXTURE_2D);
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        // Position the eye behind the origin.
        final float eyeX = 0.0f;
        final float eyeY = 0.0f;
        final float eyeZ = 1.5f;

        // We are looking toward the distance
        final float lookX = 0.0f;
        final float lookY = 0.0f;
        final float lookZ = -5.0f;

        // Set our up vector. This is where our head would be pointing were we holding the camera.
        final float upX = 0.0f;
        final float upY = 1.0f;
        final float upZ = 0.0f;

        // Set the view matrix. This matrix can be said to represent the camera position.
        // NOTE: In OpenGL 1, a ModelView matrix is used, which is a combination of a model and
        // view matrix. In OpenGL 2, we can keep track of these matrices separately if we choose.
        Matrix.setLookAtM(mViewMatrix, 0, eyeX, eyeY, eyeZ, lookX, lookY, lookZ, upX, upY, upZ);

        final String vertexShader = "uniform mat4 u_MVPMatrix; \n" +
                " \n" +
                "attribute vec4 a_position; \n" +
                "attribute vec2 a_texCoord; \n" +
                "varying vec2 v_texCoord; \n" +
                "void main() \n" +
                "{ \n" +
                "gl_Position = a_position; \n" +
                "v_texCoord = a_texCoord; \n" +
                "}";

        final String fragmentShader = "precision lowp float; \n" +
                " \n" +
                "varying vec2 v_texCoord; \n" +
                "uniform sampler2D u_samplerTexture; \n" +
                "void main() \n" +
                "{ \n" +
                "gl_FragColor = texture2D(u_samplerTexture, v_texCoord); \n" +
                "} ";
        // Load in the vertex shader.
        int vertexShaderHandle = GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER);

        if (vertexShaderHandle != 0) {
            // Pass in the shader source.
            GLES20.glShaderSource(vertexShaderHandle, vertexShader);

            // Compile the shader.
            GLES20.glCompileShader(vertexShaderHandle);

            // Get the compilation status.
            final int[] compileStatus = new int[1];
            GLES20.glGetShaderiv(vertexShaderHandle, GLES20.GL_COMPILE_STATUS, compileStatus, 0);

            // If the compilation failed, delete the shader.
            if (compileStatus[0] == 0) {
                GLES20.glDeleteShader(vertexShaderHandle);
                vertexShaderHandle = 0;
            }
        }

        if (vertexShaderHandle == 0) {
            throw new RuntimeException("Error creating vertex shader.");
        }

        // Load in the fragment shader shader.
        int fragmentShaderHandle = GLES20.glCreateShader(GLES20.GL_FRAGMENT_SHADER);

        if (fragmentShaderHandle != 0) {
            // Pass in the shader source.
            GLES20.glShaderSource(fragmentShaderHandle, fragmentShader);

            // Compile the shader.
            GLES20.glCompileShader(fragmentShaderHandle);

            // Get the compilation status.
            final int[] compileStatus = new int[1];
            GLES20.glGetShaderiv(fragmentShaderHandle, GLES20.GL_COMPILE_STATUS, compileStatus, 0);

            // If the compilation failed, delete the shader.
            if (compileStatus[0] == 0) {
                GLES20.glDeleteShader(fragmentShaderHandle);
                fragmentShaderHandle = 0;
            }
        }

        if (fragmentShaderHandle == 0) {
            throw new RuntimeException("Error creating fragment shader.");
        }


        // Create a program object and store the handle to it.
        mProgramHandle = GLES20.glCreateProgram();

        if (mProgramHandle != 0) {
            // Bind the vertex shader to the program.
            GLES20.glAttachShader(mProgramHandle, vertexShaderHandle);

            // Bind the fragment shader to the program.
            GLES20.glAttachShader(mProgramHandle, fragmentShaderHandle);
            // Link the two shaders together into a program.
            GLES20.glLinkProgram(mProgramHandle);

            // Get the link status.
            final int[] linkStatus = new int[1];
            GLES20.glGetProgramiv(mProgramHandle, GLES20.GL_LINK_STATUS, linkStatus, 0);

            // If the link failed, delete the program.
            if (linkStatus[0] == 0) {
                GLES20.glDeleteProgram(mProgramHandle);
                mProgramHandle = 0;
            }
        }

        if (mProgramHandle == 0) {
            throw new RuntimeException("Error creating program.");
        }
        //---

        // Tell OpenGL to use this program when rendering.
        mTextureDataHandle = TextureHelper.loadTexture(mActivityContext, R.drawable.left);
    }

    @Override
    public void onSurfaceChanged(GL10 glUnused, int width, int height) {
        // Set the OpenGL viewport to the same size as the surface.
        GLES20.glViewport(0, 0, width, height);

        GLES20.glDisable(GLES20.GL_DITHER);
        // Create a new perspective projection matrix. The height will stay the same
        // while the width will vary as per aspect ratio.
        final float ratio = (float) width / height;
        final float left = -ratio;
        final float right = ratio;
        final float bottom = -1.0f;
        final float top = 1.0f;
        final float near = 1.0f;
        final float far = 10.0f;

        Matrix.frustumM(mProjectionMatrix, 0, left, right, bottom, top, near, far);
    }

    float angles = 0f;
    boolean reverse;
    private int mMVPMatrixHandle;
    @Override
    public void onDrawFrame(GL10 glUnused) {
        GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GLES20.glUseProgram(mProgramHandle);
        mTextureUniformHandle = GLES20.glGetUniformLocation(mProgramHandle, "u_samplerTexture");
        mTextureCoordinateHandle = GLES20.glGetAttribLocation(mProgramHandle, "a_texCoord");
        mPositionHandle = GLES20.glGetAttribLocation(mProgramHandle, "a_position");
        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgramHandle, "u_MVPMatrix");
        GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glEnable(GLES20.GL_TEXTURE_2D);
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        if ((angles > 10f && angles <= 10.3f)||(angles > -10f && angles <= -9.7f)) {
            float temp = new Random().nextInt(100) / 100f;
            float tempv = new Random().nextInt(100) / 100f;
            if(temp<0.3f){
                temp = 0.3f;
            }
            if(tempv<0.3f){
                tempv = 0.3f;
            }
            quadVertex = new float[]{
                    //左边
                    -0.5f, 0.5f-0.3f*tempv, 0.0f, // Position 0
                    -0.5f, -0.5f, 0.0f, // Position 1
                    -0.5f+0.8f*temp, -0.5f, 0.0f, // Position 2
                    -0.5f+0.8f*temp, -0.5f, 0.0f, // Position 2
                    -0.5f+0.8f*temp, 0.5f-0.3f*tempv, 0.0f, // Position 3
                    -0.5f, 0.5f-0.3f*tempv, 0.0f, // Position 0
                    //右边
                    -0.5f+0.8f*temp, 0.5f-0.3f*tempv, 0.0f, // Position 3
                    -0.5f+0.8f*temp, -0.5f, 0.0f, // Position 2
                    0.5f, -0.5f, 0.0f, // Position 5
                    0.5f, -0.5f, 0.0f, // Position 5
                    0.5f, 0.5f-0.3f*tempv, 0.0f, // Position 4
                    -0.5f+0.8f*temp, 0.5f-0.3f*tempv, 0.0f// Position 3
            };
//            quadVertex = new float[]{
//                    //左边
//                    -0.71f, 0.0f, 0.0f, // Position 0
//                    -0.0f, -0.71f, 0.0f, // Position 1
//                    0.35f, -0.35f, 0.0f, // Position 2
//                    0.35f, -0.35f, 0.0f, // Position 2
//                    -0.35f, 0.35f, 0.0f, // Position 3
//                    -0.71f, 0.0f, 0.0f, // Position 0
//                    //右边
//                    -0.35f, 0.35f, 0.0f, // Position 3
//                    0.35f, -0.35f, 0.0f, // Position 2
//                    0.71f, -0.0f, 0.0f, // Position 5
//                    0.71f, -0.0f, 0.0f, // Position 5
//                    0.0f, 0.71f, 0.0f, // Position 4
//                    -0.35f, 0.35f, 0.0f // Position 3
//            };
            for(int i=0;i<quadVertex.length;i++){
//                quadVertex[i] = quadVertex[i]/2;
            }
            float[] textArr = new float[]{

                    0f, 0.0f, // TexCoord 0
                    0, 1.0f, // TexCoord 1
                    0.5f, 1.0f, // TexCoord 2

                    0.5f, 1.0f, // TexCoord 2
                    0.5f, 0.0f, // TexCoord 3
                    0f, 0.0f, // TexCoord 0
//
                    0.5f, 0.0f, // TexCoord 3
                    0.5f, 1.0f, // TexCoord 2
                    1.0f, 1.0f, // TexCoord 5

                    1.0f, 1.0f, // TexCoord 5
                    1.0f, 0.0f, // TexCoord 4
                    0.5f, 0.0f, // TexCoord 3
            };
//            mTriangle1Vertices = ByteBuffer.allocateDirect(quadVertex.length * mBytesPerFloat)
//                    .order(ByteOrder.nativeOrder()).asFloatBuffer();
            mTriangle1Vertices.clear();
            mTriangle1Vertices.put(quadVertex).position(0);
            mCubeTextureCoordinates.clear();
            mCubeTextureCoordinates.put(textArr).position(0);
        }
        // Do a complete rotation every 10 seconds.
        if (reverse) {
            angles = angles - 0.2f;
            if (angles < -20f) {
                reverse = false;
            }
        } else {
            angles = angles + 0.2f;
            if (angles > 20f) {
                reverse = true;
            }
        }
//        Matrix.rotateM(mModelMatrix, 0, angles, 0.0f, 0.0f, 1.0f);
        //------------

        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        // Bind the texture to this unit.
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureDataHandle);
        // Tell the texture uniform sampler to use this texture in the shader by binding to texture unit 0.
        GLES20.glUniform1i(mTextureUniformHandle, 0);
        // Draw the triangle facing straight on.
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureDataHandle);

        mTriangle1Vertices.position(0);
        GLES20.glVertexAttribPointer(mPositionHandle, 3, GLES20.GL_FLOAT, false, 0, mTriangle1Vertices);
        GLES20.glEnableVertexAttribArray(mPositionHandle);

        mCubeTextureCoordinates.position(0);
        GLES20.glVertexAttribPointer(mTextureCoordinateHandle, 2, GLES20.GL_FLOAT, false,
                0, mCubeTextureCoordinates);
        GLES20.glEnableVertexAttribArray(mTextureCoordinateHandle);

        Matrix.multiplyMM(mMVPMatrix, 0, mViewMatrix, 0, mModelMatrix, 0);

        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mMVPMatrix, 0);

        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mMVPMatrix, 0);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 12);
    }

    @Override
    public void onDestroy() {

    }
}
