package rru.mp3player;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import android.app.ListActivity;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

@SuppressWarnings("unused")
class Mp3Filter implements FilenameFilter {
    public boolean accept(File dir, String name) {
        return (name.endsWith(".mp3"));
    }
}

public class mp3player extends ListActivity {
    
	private static final String MEDIA_PATH = new String("/sdcard/");
	private List<String> songs = new ArrayList<String>();
	private MediaPlayer mp = new MediaPlayer();
	
	@Override
    public void onCreate(Bundle icicle) {
        try {
        	super.onCreate(icicle);
        	setContentView(R.layout.songlist);
        	updateSongList();
        } catch (NullPointerException e) {
        	Log.v(getString(R.string.app_name), e.getMessage());
        }
    }
    
    public void updateSongList() {
    	File home = new File(MEDIA_PATH);
		if (home.listFiles( new Mp3Filter()).length > 0) {
    		for (File file : home.listFiles( new Mp3Filter())) {
    			songs.add(file.getName());
    		}
		
    		ArrayAdapter<String> songList = new ArrayAdapter<String>(this,R.layout.song_item,songs);
    		setListAdapter(songList);
		}    	
    }
    
	@Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
		try {
			
			mp.reset();
			mp.setDataSource(MEDIA_PATH + songs.get(position));
			mp.prepare();
			mp.start();
		} catch(IOException e) {
			Log.v(getString(R.string.app_name), e.getMessage());
		} 
	}
}