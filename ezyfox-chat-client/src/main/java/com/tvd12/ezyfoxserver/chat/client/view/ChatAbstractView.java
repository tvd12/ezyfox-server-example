package com.tvd12.ezyfoxserver.chat.client.view;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.tvd12.ezyfox.function.EzyApply;
import com.tvd12.ezyfoxserver.chat.client.ChatApplication;
import com.tvd12.ezyfoxserver.chat.client.ChatSingleton;
import com.tvd12.ezyfoxserver.chat.client.constant.ChatEventType;
import com.tvd12.ezyfoxserver.chat.client.controller.ChatController;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * Created by tavandung12 on 6/22/17.
 */
public abstract class ChatAbstractView implements ChatView {

	protected final Scene scene;
	protected final Stage stage;
	protected final Parent parent;
    protected final FXMLLoader loader;
	protected final ChatController controller;
	protected final Map<Object, EzyApply<Object>> updaters = defaultUpdaters();

    public ChatAbstractView() {
    	try {
    		stage = new Stage();
        	controller = getController();
    		loader = new FXMLLoader();
    		loader.setController(controller);
            loader.setLocation(getClass().getResource(getViewPath()));
            updateLoader(loader);
            parent = loader.load();
            scene = new Scene(parent);
            updateScene(scene);
            stage.setTitle(getClass().getSimpleName());
            stage.setScene(scene);
            updateStage(stage);
        }
        catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
    
    protected abstract String getViewPath();
    protected abstract ChatController getController();
    
    protected void updateScene(Scene scene) {}

    protected void updateStage(Stage stage) {
        stage.setOnCloseRequest(e -> exitApplication());
    }
    
    protected void updateLoader(FXMLLoader loader) {}
    
    @Override
    public void update(Object cmd, Object data) {
    	if(updaters.containsKey(cmd))
    		updaters.get(cmd).apply(data);
    }

    @Override
    public void show() {
        beforeShow();
        doShow();
    }
    
    protected void beforeShow() {}
    
    protected void doShow() {
    	stage.show();
    }

    @Override
    public void hide() {
    	beforeHide();
    	doHide();
    }
    
    protected void beforeHide() {}
    
    protected void doHide() {
    	stage.hide();
    }

    protected void exitApplication() {
        ChatApplication.exit(0);
    }
    
    protected ChatViewFactory getViewFactory() {
    	return ChatSingleton.getInstance().getViewFactory();
    }
    
    protected ChatView getView(ChatEventType chatEventType, String modelResult) {
    	return getViewFactory().getView(chatEventType, modelResult);
    }
    
    @SuppressWarnings("unchecked")
	protected <T> T getViewItem(String itemName) {
		return (T) loader.getNamespace().get(itemName);
	}
    
    protected Map<Object, EzyApply<Object>> defaultUpdaters() {
    	Map<Object, EzyApply<Object>> answer = new ConcurrentHashMap<>();
    	addUpdaters(answer);
    	return answer;
    }
    
    protected void addUpdaters(Map<Object, EzyApply<Object>> updaters) {
    }
}
