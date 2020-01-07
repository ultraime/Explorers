package com.ultraime.game.gdxtraime.carte;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;

//BatchTiledMapRenderer
public class Carte {

	/**
	 * Element tilemap
	 */
	public TiledMap tiledMap;

	/**
	 * Element qui sera afficher. Il contient un TileMap
	 */
	public OrthogonalTiledMapRenderer rendererMap;

	/**
	 * @param carteChemin
	 */
	public Carte(final String carteChemin) {
		tiledMap = new TmxMapLoader().load(carteChemin);
		rendererMap = new OrthogonalTiledMapRenderer(tiledMap, 1);
	}

	public TiledMapTileSet getTileSet(final String tileName) {
		return tiledMap.getTileSets().getTileSet(tileName);
	}

	/**
	 * @param LayerPosition
	 * @param String depart (nom de l'objet dans tilemap)
	 * @return Vector2 posDepart
	 */
	public Vector2 recupererPositionDepart(String LayerPosition, String depart) {
		final Vector2 posDepart = new Vector2();
		for (MapObject object : tiledMap.getLayers().get(LayerPosition).getObjects()) {
			if (object.getName().equals(depart)) {
				posDepart.x = (Float) object.getProperties().get("x");
				posDepart.y = (Float) object.getProperties().get("y");
				break;
			}
		}
		return posDepart;
	}

	public List<MapObject> recupererEvents(String LayerPosition, String event) {
		List<MapObject> listEvent = new ArrayList<MapObject>();

		for (MapObject object : tiledMap.getLayers().get(LayerPosition).getObjects()) {
			if (object.getName().equals(event)) {
				listEvent.add(object);
			}
		}
		return listEvent;
	}

	public void render() {
		this.rendererMap.render();

	}

	public void updateCamera(OrthographicCamera camera) {
		rendererMap.setView(camera);
	}

	public TiledMapTileLayer getLayers(String string) {
		return (TiledMapTileLayer) tiledMap.getLayers().get(string);
	}

	/**
	 * @param posX
	 * @param posY
	 * @param tiledMapTileLayer string
	 */
	public void viderCellMap(final int posX, final int posY, final String tiledMapTileLayer) {
		if (getLayers(tiledMapTileLayer).getCell(posX, posY) != null) {
			getLayers(tiledMapTileLayer).setCell(posX, posY, null);
		}
	}

	public void dispose() {
		this.rendererMap.dispose();
		this.tiledMap.dispose();
	}

}
