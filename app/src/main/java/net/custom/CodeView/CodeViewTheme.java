package net.custom.CodeView;

/**
 * Created by yzr on 16/6/21.
 */
public enum CodeViewTheme {
    DEFAULT("default","#F0F0F0"),
    AGATE("agate","#030303"),
    ANDROIDSTUDIO("androidstudio","#282b2e"),
    ARTA("arta","#020202"),
    TOMORROW_NIGHT("tomorrow-night","#1d1f21");

    private String name;
    private String backgroundColor;

    CodeViewTheme(String name, String backgroundColor){
        this.name=name;
        this.backgroundColor=backgroundColor;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public String getName() {
        return name;

    }

    private static CodeViewTheme[]themes;

    public static CodeViewTheme[]listThemes(){
        if(themes==null){
            themes=new CodeViewTheme[]{
                    DEFAULT,AGATE,ANDROIDSTUDIO,ARTA,TOMORROW_NIGHT
            };
        }
        return themes;
    }
}