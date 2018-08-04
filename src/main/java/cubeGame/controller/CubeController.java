package main.java.cubeGame.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.Timer;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;
import main.java.cubeGame.model.CubeWorld;
import main.java.cubeGame.model.Die;
import main.java.cubeGame.view.CubeGameScreen;
import main.java.menu.controller.MGController;

/**
 *  * @author Cathrine and Collin
 *Controls the Cube game. Detects screen size and creates a cubeWorld and an animation.
 */
public class CubeController extends MGController{
	final int timerTick = 17;
	CubeWorld world;
	CubeGameScreen view;
	CubeListener listener;
	Timer timer; //Constantly update the view and model
	
	final String SHEET_URL = "https://docs.google.com/spreadsheets/d/1WGQgmEM0d8eYutIDekvzvsNEpAWS50jq0auys3wC9MY/edit?usp=sharing";
	final String SHEET_ID = "1WGQgmEM0d8eYutIDekvzvsNEpAWS50jq0auys3wC9MY";
	private static final String APPLICATION_NAME = "Google Sheets API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved credentials/ folder.
     */
    private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS_READONLY);
    private static final String CREDENTIALS_FILE_PATH = "credentials.json";
	
	public boolean recording = false;
	
	public CubeController(){
		view = new CubeGameScreen(this);
		world = new CubeWorld();
		listener = new CubeListener(this);
		timer = new Timer (timerTick, listener); // The timer triggers an actionevent in cubeListener every frame
		view.addMouseListener(listener);
		view.addMouseMotionListener(listener);
		timer.start();
		
	}
	
	/**
     * Creates an authorized Credential object.
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = CubeController.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
    }
	@Override
	public void dispose() {
		world.reset();
		view.setVisible(false);
		timer.stop();
		view.reset();
	}

	public CubeWorld getWorld() {
		return world;
	}
	
	@Override
	public CubeGameScreen getView() {
		return view;
	}
	
	public Timer getTimer() {
		return timer;
	}
	
	public void submit() {
		List<String> values = Arrays.stream(world.getDice()).map(die -> die.getName()).collect(Collectors.toList());
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
	}
}
