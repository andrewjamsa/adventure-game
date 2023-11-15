package AdventureModel.Effects;

public abstract class HideableEffect implements EffectStrategy{
    private boolean hide = true;
    public void setHide(boolean hide){
        this.hide=hide;
    }

    abstract protected String realDescription();

    @Override
    public String getDescription(){
        if(hide){
            return "???";
        }
        else {
            return realDescription();
        }
    }
}
