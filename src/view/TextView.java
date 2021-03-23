package view;

import view.textMenu.TextMenu;

public class TextView implements View {
    TextMenu menu;

    public TextView(TextMenu menu) {
        this.menu = menu;
    }

    @Override
    public void run() {
        menu.show(false);
    }
}
