package com.backinfile.KnightProj;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

/**
 * 游戏主程序的入口类, 实现 ApplicationListener 接口
 */
public class MainGame implements ApplicationListener {

	@Override
	public void create() {
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void resume() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void render() {
		/*
		 * 设置清屏颜色为红色（RGBA）,
		 * 
		 * LibGDX 中使用 4 个浮点类型变量（值范围 0.0 ~ 1.0）表示一个颜色（分别表示颜色的 RGBA 四个通道）,
		 * 
		 * 十六进制颜色与浮点颜色之间的转换: 将十六进制颜色的每一个分量除以 255 得到的浮点数就是浮点颜色对应的通道值。
		 */
		Gdx.gl.glClearColor(1, 0, 0, 1);

		// 清屏
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}

	@Override
	public void dispose() {
	}
}