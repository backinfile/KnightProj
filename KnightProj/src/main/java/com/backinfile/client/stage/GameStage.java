package com.backinfile.client.stage;

import com.backinfile.client.ResourceManager;
import com.backinfile.client.actor.DefaultLabel;
import com.backinfile.client.actor.LineButton;
import com.backinfile.client.actor.RoomListView;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Slider.SliderStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class GameStage extends BaseStage {

	RoomListView roomListView;

	public GameStage() {
		super(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
	}

	@Override
	public void init() {
//		addActor(new StageBackground(ResourceManager.IntroBackground, 0.2f));
//		roomListView = new RoomListView(this);

		Table container = new Table();
		addActor(container);
		container.setFillParent(true);

		Table table = new Table();
		// table.debug();

		ScrollPane.ScrollPaneStyle st = new ScrollPane.ScrollPaneStyle();
		st.hScroll = new TextureRegionDrawable(ResourceManager.DefaultButtonUp);
		st.hScrollKnob = new TextureRegionDrawable(ResourceManager.DefaultButtonDown);
		final ScrollPane scroll = new ScrollPane(table, st);

		InputListener stopTouchDown = new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				event.stop();
				return false;
			}
		};

		table.pad(10).defaults().expandX().space(4);
		for (int i = 0; i < 100; i++) {
			table.row();
			table.add(new DefaultLabel(i + "uno")).expandX().fillX();

			TextButton button = new LineButton(i + "dos");
			table.add(button);
			button.addListener(new ClickListener() {
				public void clicked(InputEvent event, float x, float y) {
					System.out.println("click " + x + ", " + y);
				}
			});

			SliderStyle style = new SliderStyle();
			Slider slider = new Slider(0, 100, 1, false, style);
			slider.addListener(stopTouchDown); // Stops touchDown events from propagating to the FlickScrollPane.
			table.add(slider);

			table.add(new DefaultLabel(
					i + "tres long0 long1 long2 long3 long4 long5 long6 long7 long8 long9 long10 long11 long12"));
		}

		final TextButton flickButton = new LineButton("Flick Scroll");
		flickButton.setChecked(true);
		flickButton.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				scroll.setFlickScroll(flickButton.isChecked());
			}
		});

		final TextButton fadeButton = new LineButton("Fade Scrollbars");
		fadeButton.setChecked(true);
		fadeButton.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				scroll.setFadeScrollBars(fadeButton.isChecked());
			}
		});

		final TextButton smoothButton = new LineButton("Smooth Scrolling");
		smoothButton.setChecked(true);
		smoothButton.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				scroll.setSmoothScrolling(smoothButton.isChecked());
			}
		});

		final TextButton onTopButton = new LineButton("Scrollbars On Top");
		onTopButton.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				scroll.setScrollbarsOnTop(onTopButton.isChecked());
			}
		});

		container.add(scroll).expand().fill().colspan(4);
		container.row().space(10).padBottom(10);
		container.add(flickButton).right().expandX();
		container.add(onTopButton);
		container.add(smoothButton);
		container.add(fadeButton).left().expandX();

	}

}
