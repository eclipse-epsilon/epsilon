class PictoToolbar {

    toolbar;
    self;
    scheduledToHide = false;
    target;
    mouseIsOver = false;

    constructor() {
        self = this;
        self.traceManager = new PictoTraceManager();
        self.toolbar = self.#createToolbar();
        self.toolbar.onmouseover = function() {
            self.mouseIsOver = true;
        }
        self.toolbar.onmouseout = function() {
            self.mouseIsOver = false;
        }
        addEventListener("mouseover", (event) => {
            if (self.traceManager.isTraceable(event.target)) {
                //console.log(event.target + " -> showing ")
                self.show(event, event.target.textContent);
            }
            else {
                if (!self.contains(event.target)) {
                    //console.log(event.target + " -> hiding ")
                    self.hide();
                }
            }
        });
    }

    show(event, text) {
        this.scheduledToHide = false;
        if (event.target != self.target && !self.contains(event.target)) {
            self.target = event.target;
            self.toolbar.style.position = "absolute";
            self.toolbar.style.left = event.pageX + 5 + "px";
            self.toolbar.style.top = event.pageY + 5 + "px";
            self.toolbar.style.display = "block";
        }
    }

    hide() {
        if (self.toolbar.style.display != "none") {
            self.scheduledToHide = true;
            setTimeout(self.#doHide, 500);
        }
    }

    #doHide() {
        if (self.scheduledToHide && !self.mouseIsOver) {
            self.toolbar.style.display = "none";
            self.target = null;
        }
    }

    #createToolbar() {
        var toolbar = document.createElement("div");
        document.body.appendChild(toolbar);
        toolbar.classList.add("picto-edit-toolbar");
        toolbar.appendChild(self.#createAction("locate"));
        toolbar.appendChild(self.#createAction("edit"));
        toolbar.appendChild(self.#createAction("delete"));
        toolbar.style.display = "none";
        return toolbar;
    }

    #createAction(action) {
        var button = document.createElement("button");
        button.classList.add("picto-edit-toolbar-button");
        button.type = "button";
        var image = document.createElement("img");
        image.classList.add("picto-toolbar-button-" + action);
        button.appendChild(image);
        button.onclick = function() {
            window["picto_toolbar_" + action](self.traceManager.getTrace(self.target));
        }
        return button;
    }

    contains(node) {
        return self.toolbar.contains(node);
    }

}

class PictoTraceManager {

    isTraceable(node) {
        if (node.children.length == 0) {
            return this.#getInvisibleCharactersSuffix(node.textContent).length > 0;
        }
        else {
            return false;
        }
    }

    getTrace(node) {
        return this.#getInvisibleCharactersSuffix(node.textContent);
    }

    #getInvisibleCharactersSuffix(text) {
        var position = text.length - 1;
        var suffix = "";
        while (position >= 0 && text.charCodeAt(position) >= 8288 && text.charCodeAt(position) <= 8292) {
            suffix += text.charAt(position);
            position --;
        }
        return suffix;
    }
}

/*
function edit(suffix) {
    window.alert("edit " + suffix);
}

function del(suffix) {
    window.alert("delete " + suffix);
}*/

new PictoToolbar();