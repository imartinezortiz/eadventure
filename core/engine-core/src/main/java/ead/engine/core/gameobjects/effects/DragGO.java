package ead.engine.core.gameobjects.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.google.inject.Inject;

import ead.common.model.elements.effects.DragEf;
import ead.common.model.elements.huds.BottomHud;
import ead.common.model.elements.operations.SystemFields;
import ead.common.model.params.guievents.DragGEv;
import ead.common.model.params.guievents.MouseGEv;
import ead.common.model.params.guievents.enums.DragGEvType;
import ead.engine.core.events.DragEvent;
import ead.engine.core.game.interfaces.GUI;
import ead.engine.core.game.interfaces.GameState;
import ead.engine.core.gameobjects.sceneelements.SceneElementGO;

public class DragGO extends AbstractEffectGO<DragEf> {

	private GUI gui;

	private SceneElementGO target;

	private SceneElementGO parent;

	private SceneElementGO currentGO;

	private int z;

	private float x;

	private float y;

	private SceneElementGO hud;

	private boolean done;

	private float mouseInitX;

	private float mouseInitY;

	private float diffX;

	private float diffY;

	@Inject
	public DragGO(GameState gameState, GUI gui) {
		super(gameState);
		this.gui = gui;
	}

	public void initialize() {
		super.initialize();
		done = false;
		target = (SceneElementGO) action.getTarget();
		parent = (SceneElementGO) target.getParent();
		x = target.getRelativeX();
		y = target.getRelativeY();
		z = target.getZ();
		hud = gui.getHUD(BottomHud.ID);
		parent.removeActor(target);
		target.setZ(0);
		hud.addSceneElement(target);
		mouseInitX = gameState.getValue(SystemFields.MOUSE_SCENE_X);
		mouseInitY = gameState.getValue(SystemFields.MOUSE_SCENE_Y);
		diffX = x - mouseInitX;
		diffY = y - mouseInitY;
		target.handle(new DragEvent(MouseGEv.MOUSE_START_DRAG));
	}

	public void act(float delta) {
		target.setX(gameState.getValue(SystemFields.MOUSE_SCENE_X) + diffX);
		target.setY(gameState.getValue(SystemFields.MOUSE_SCENE_Y) + diffY);
		SceneElementGO go = gui.getScene().getFirstGOIn(
				gameState.getValue(SystemFields.MOUSE_SCENE_X),
				gameState.getValue(SystemFields.MOUSE_SCENE_Y));
		if (go != currentGO) {
			if (currentGO != null) {
				// SceneElement id and definition id
				currentGO.handle(new DragEvent(new DragGEv(target.getName(),
						DragGEvType.EXITED)));
				currentGO.handle(new DragEvent(new DragGEv(target.getElement()
						.getDefinition().getId(), DragGEvType.EXITED)));
			}
			currentGO = go;
			if (currentGO != null) {
				// SceneElement id and definition id
				currentGO.handle(new DragEvent(new DragGEv(target.getName(),
						DragGEvType.ENTERED)));
				currentGO.handle(new DragEvent(new DragGEv(target.getElement()
						.getDefinition().getId(), DragGEvType.ENTERED)));
			}
		}
		done = !Gdx.input.isButtonPressed(Buttons.LEFT);
	}

	public boolean isFinished() {
		return done;
	}

	public void finish() {
		if (effect.isReturnAfterDrag()) {
			target.setX(x);
			target.setY(y);
		} else {
			// XXX Objects can get outside of the window
			target.setX(x + gameState.getValue(SystemFields.MOUSE_SCENE_X)
					- mouseInitX);
			target.setY(y + gameState.getValue(SystemFields.MOUSE_SCENE_Y)
					- mouseInitY);
		}
		target.setZ(z);
		parent.addSceneElement(target);
		target.handle(new DragEvent(MouseGEv.MOUSE_DROP));
		if (currentGO != null) {
			// SceneElement id and definition id
			currentGO.handle(new DragEvent(new DragGEv(target.getName(),
					DragGEvType.DROP)));
			currentGO.handle(new DragEvent(new DragGEv(target.getElement()
					.getDefinition().getId(), DragGEvType.DROP)));
		}
		super.finish();
	}

	public boolean isQueueable() {
		return true;
	}

}