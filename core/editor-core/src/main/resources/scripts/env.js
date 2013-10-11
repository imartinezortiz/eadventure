/** 
 * Globally-defined functions, always accessible.
 * This file is loaded before r.java
 */

// access to logging
var log = Packages.org.slf4j.LoggerFactory.getLogger('js');

// programmatically set log-level
function setLogLevel(logger, level) {
	Packages.es.eucm.ead.editor.util.Log4jConfig.setLevel(logger, level);
}

// load-from-js
function load(name) {
	var utils = Packages.es.eucm.ead.tools.java.utils;
	if (name[0] == '.') {
		name = '/scripts' + name.substring(1);
	}
	log.debug('loading `' + name + '`');
	// important: Java Strings are not actually JS Strings; they can be appended, though
	eval('' + utils.FileUtils.loadResourceToString(name));
}

// load (rhino-compatible, abridged) require.js
load('/scripts/r.js');

// dump JSONified arguments
function dump() {
	for (var i=0; i<arguments.length; i++) {
		print(JSON.stringify(arguments[i]));
	}
}

var cmd = {
	
	// clear log
	clear: function() {
		cmd.panel.log.setText("");
	},
	// save log
	save: function(f) {
		var utils = Packages.es.eucm.ead.tools.java.utils;
		var file = new Packages.java.io.File(f);
		utils.FileUtils.writeStringToFile(cmd.panel.log.text, file);
	}
}

var ed = {
	
	// access to editor-package
	package: Packages.es.eucm.ead.editor,
			
	// access to main controllers & current model
	view:    controller.getViewController(),
	command: controller.getCommandManager(),
	project: controller.getProjectController(),
	script:  controller.getScriptController(),
	getModel:   function() { return controller.getModel(); },
	getAction:	function(name) { return controller.getAction(name); }
};


var help =
"    Help\n\
-------------- debug and load JS  --------------\n\
 print(x)			print a variable value\n\
 dump(...)			dump JSONified args\n\
 log				direct access to a logger (use as log.debug(), log.info(), ...)\n\
 setLogLevel(x,y)	set log-level of prefix 'x' to level 'y'\n\
 load(f)			load a JS file 'f' (internally using getResourceAsStream); '.' is\n\
                    substituted for '/scripts/'\n\
 require(d,c)		load RequireJS dependency; d is an array of dependency names, \n\
                    c is a callback (which will receive, as params, the return-values of\n\
					these dependencies\n\
-------------- access to packages --------------\n\
 Packages.x.y.Z		access to the 'x.y.Z' class (or package)\n\
 controller			access to the editor's main controller\n\
-------------- 'cmd' helper object --------------\n\
panel				this command console object (per-panel unique)\n\
panel.log.text		string with all the log text in this console\n\
cmd.clear()	        clear panel text\n\
   .save(f)         save panel text\n\
-------------- 'ed' helper object --------------\n\
 ed.package         same as Packages.es.eucm.ead.editor\n\
   .view			access to view-controller\n\
   .command			access to command-manager (undo/redo)\n\
   .project			access to project-controller (load/save/import/export/run)\n\
   .script			access to script-controller (refreshScripts, eval)\n\
   .view			access to view-controller\n\
   .getModel()      currenty-loaded model\n\
   .getAction(a)    retrieves editor-action 'a'; execute with actionPerformed()\n\
";
