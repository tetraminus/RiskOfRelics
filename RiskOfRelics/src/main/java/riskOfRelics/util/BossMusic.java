//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package riskOfRelics.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.backends.headless.mock.audio.MockMusic;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.audio.MainMusic;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.MathHelper;
import jdk.tools.jmod.Main;
import org.apache.logging.log4j.Logger;
import riskOfRelics.RiskOfRelics;

public class BossMusic extends MainMusic implements Music.OnCompletionListener{
    private static final Logger logger = RiskOfRelics.logger;
    private Music music;
    public String key;
    private static final String INTRO = "riskOfRelicsResources/music/AmbryThemeIntro.ogg";
    private static final String PART1 = "riskOfRelicsResources/music/AmbryThemePart1.ogg";

    public boolean isSilenced = false;
    private float silenceTimer = 0.0F;
    private float silenceTime = 0.0F;
    private static final float SILENCE_TIME = 4.0F;
    private static final float FAST_SILENCE_TIME = 0.25F;
    private float silenceStartVolume;
    private static final float FADE_IN_TIME = 4.0F;
    private static final float FADE_OUT_TIME = 4.0F;
    private float fadeTimer = 0.0F;
    public boolean isFadingOut = false;
    private float fadeOutStartVolume;
    public boolean isDone = false;
    private AbstractCreature boss;
    private boolean isIntro = true;

    public BossMusic(AbstractCreature m) {
        super(INTRO);
        isIntro = true;
        this.boss = m;
        this.music = this.getSong(boss);// 52
        this.fadeTimer = 4.0F;// 53
        this.music.setLooping(false);// 54
        this.music.play();// 55
        //this.music.setVolume(0.0F);// 56
        unsilence();

    }// 57

    private Music getSong(AbstractCreature m) {
        Music temp;
        if (isIntro){
            temp =  newMusic(INTRO);
        } else {
            temp =  newMusic(PART1);
        }
        temp.setOnCompletionListener(this);
        return temp;
    }// 65

    @Override
    public void updateVolume() {
        super.updateVolume();
        logger.info(music);
    }

    @Override
    public void onCompletion(Music music) {

        isIntro = false;
        this.music = this.getSong(boss);
        music.setOnCompletionListener(this);
        this.fadeTimer = 0F;
        this.music.setLooping(true);
        this.music.play();
        unsilence();
        //this.music.setVolume(0.0F);

    }
}
