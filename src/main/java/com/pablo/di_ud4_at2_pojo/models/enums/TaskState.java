package com.pablo.di_ud4_at2_pojo.models.enums;

import de.jensd.fx.glyphs.icons525.Icons525;

public enum TaskState {
    NONE("Sin estado", Icons525.NOENTRY),
    NON_STARTED("Sin empezar", Icons525.TARGET),
    WIP("En progreso", Icons525.PLAY_CIRCLE),
    FINISHED("Terminado", Icons525.CHECKBOX_CHECKED);

    private final String label;
    private final Icons525 icon;

    TaskState(String label, Icons525 icon){
        this.label = label;
        this.icon = icon;
    }

    public Icons525 getIcon() {
        return icon;
    }

    @Override
    public String toString() {
        return label;
    }
}
