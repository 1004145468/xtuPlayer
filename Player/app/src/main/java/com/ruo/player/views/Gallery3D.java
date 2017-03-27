package com.ruo.player.views;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Transformation;
import android.widget.Gallery;
import android.widget.ImageView;

public class Gallery3D extends Gallery {
    //最大的旋转角度
    private int maxRotateAngle = 50;
    //最大缩放值
    private int maxZoom = -250;
    //记录中间点的位置
    private int currentOfGallery;
    //创建相机对象
    private Camera camera = new Camera();

    public Gallery3D(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setStaticTransformationsEnabled(false);
    }

    public Gallery3D(Context context, AttributeSet attrs) {
        super(context, attrs);
        //指定图形是否变化 false:否  true:是
        setStaticTransformationsEnabled(false);
    }

    public Gallery3D(Context context) {
        super(context);
        setStaticTransformationsEnabled(false);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        currentOfGallery = getCurrentOfGallery();
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected boolean getChildStaticTransformation(View child, Transformation t) {
        //得到图片的中心点
        int currentOfChild = getCurrentOfView(child);
        int width = child.getLayoutParams().width;
        int height = child.getLayoutParams().height;
        //旋转的角度
        int rotateAngle = 0;
        t.clear();
        //设置图片变形样式
        t.setTransformationType(Transformation.TYPE_MATRIX);
        //位置中心点位置
        if (currentOfChild == currentOfGallery) {
            transformationBitmap(child, t, 0);
        } else {  //不是中心位置
            rotateAngle = (int) ((float) (currentOfGallery - currentOfChild) / width * maxRotateAngle);
            if (Math.abs(rotateAngle) > maxRotateAngle) {
                rotateAngle = rotateAngle < 0 ? -maxRotateAngle : maxRotateAngle;
            }
            //图片变形
            transformationBitmap(child, t, rotateAngle);
        }
        return true;
    }

    /**
     * 图片变形
     *
     * @param child
     * @param t
     * @param i
     */
    private void transformationBitmap(View child, Transformation t, int rotateAngle) {
        //保存图像变化的效果
        camera.save();
        Matrix imageMatrix = t.getMatrix();
        int rotate = Math.abs(rotateAngle);
        int imageWidth = child.getWidth();
        int imageHeight = child.getHeight();
        //x:水平移动
        //y:垂直移动
        //z:正数:图片变大
        camera.translate(0.0f, 0.0f, 100.0f);
        //当前旋转角度小于最大旋转角度
        if (rotate < maxRotateAngle) {
            float zoom = (float) ((rotate * 1.5) + maxZoom);
            camera.translate(0.0f, 0.0f, zoom);
            //设置图片渐变效果
            child.setAlpha((int) (255 - rotate * 2.5));
        }
        //图片向展示中心进行垂直角度旋转
        camera.rotateY(rotateAngle);
        camera.getMatrix(imageMatrix);

        imageMatrix.preTranslate(-(imageWidth / 2), -(imageHeight / 2));
        imageMatrix.postTranslate(imageWidth / 2, imageHeight / 2);
        //还原图像变化的效果
        camera.restore();
    }

    /**
     * 获取Gallery展示图片的中心点
     *
     * @return
     */
    public int getCurrentOfGallery() {
        return (getWidth() - getPaddingLeft() - getPaddingRight()) / 2 + getPaddingLeft();
    }

    /**
     * 获取图片中心点
     *
     * @param view
     * @return
     */
    public int getCurrentOfView(View view) {
        return view.getLeft() + view.getWidth() / 2;
    }
}