#
#  Parameters:
#
#    MC = Minecraft version number
#

PROJECT := LightingMod
BACKUP_VOL := /Volumes/GREGTHUMB5

MC := 1.2.5
MODNAME := GregsLighting
MODVER := 1.2
MODPKG := gcewing/lighting
WEBSUBDIR :=

BACKUP_SUBDIR := ProjectsD/Minecraft/$(PROJECT)
BACKUP_DIR := $(BACKUP_VOL)/$(BACKUP_SUBDIR)
BACKUP_EXCLUDE := --exclude build --exclude jars --exclude Releases --exclude Test

DOTMC := /Users/greg/Library/Application\ Support/minecraft
PROFILE := /Local/Games/Minecraft/Profiles/1.2.5-Forge-3.3.8
SERVER := /Local/Games/Minecraft/Servers/1.2.5-Forge-3.3.8

SRC_INCLUDE := doc Makefile README.html resources src

MCP := mcp-$(MC)
CPATH_CLIENT := $(MCP)/bin/minecraft:$(MCP)/jars/bin/lwjgl.jar
CPATH_SERVER := $(MCP)/bin/minecraft_server
SPATH_COMMON := src/common:src/lib
SPATH_CLIENT := src/client:$(SPATH_COMMON)
SPATH_SERVER := src/server:$(SPATH_COMMON)
CDEST_CLIENT := build/$(MC)/client
CDEST_SERVER := build/$(MC)/server
RDEST := $(MCP)/bin/minecraft/$(MODPKG)/resources
MODCLASS_CLIENT := mod_$(MODNAME)
MODCLASS_SERVER := mod_$(MODNAME)
RELJAR_CLIENT := $(MODNAME)-Client.jar
RELJAR_SERVER := $(MODNAME)-Server.jar
RELZIP := $(MODNAME)-$(MODVER)-mc$(MC).zip
RELSRC := $(MODNAME)-$(MODVER)-mc$(MC)-Source.zip
WEBDIR := web/minecraft/mods/$(MODNAME)/$(WEBSUBDIR)

#JFLAGS = -Xlint:deprecation
MCUSER = gcewing

JC := javac $(JFLAGS)

test_client: compile_client install_client run_client

test_server: compile_server install_server run_server

compile: compile_client compile_server

compile_client: $(CDEST_CLIENT)
	rm -rf $(CDEST_CLIENT)/*
	$(JC) -classpath $(CPATH_CLIENT) -sourcepath $(SPATH_CLIENT) -d $(CDEST_CLIENT) \
    `find src/client src/common src/lib -name '*.java'`

compile_server: $(CDEST_SERVER)
	rm -rf $(CDEST_SERVER)/*
	$(JC) -classpath $(CPATH_SERVER) -sourcepath $(SPATH_SERVER) -d $(CDEST_SERVER) \
    $(JFLAGS) `find src/server src/common src/lib -name '*.java'`

install_client:
	rm -rf $(MCP)/bin/minecraft/$(MODPKG) 
	cp -r build/$(MC)/client/* $(MCP)/bin/minecraft
	mkdir -p $(RDEST)
	cp -r resources/* $(RDEST)

install_server:
	rm -rf $(MCP)/bin/minecraft_server/$(MODPKG)
	cp -r build/$(MC)/server/* $(MCP)/bin/minecraft_server

install: install_client install_server

run_client:
	cd $(MCP); ./startclient.sh $(MCUSER)

run_server:
	cd $(MCP); ./startserver.sh

install_jars:
	cp jars/$(MODNAME)-Client.jar $(PROFILE)/mods
	cp jars/$(MODNAME)-Server.jar $(SERVER)/mods

run_client_jar:
	rm -f $(DOTMC)
	ln -s $(PROFILE) $(DOTMC)
	cd $(PROFILE); \
		java -Xincgc -Xms1024M -Xmx1024M \
			-cp bin/minecraft.jar:bin/jinput.jar:bin/lwjgl_util.jar:bin/lwjgl.jar \
			-Djava.library.path=bin/natives \
			net.minecraft.client.Minecraft $(MCUSER)

test_client_jar: compile install release install_jars run_client_jar

release: reob relpkg

relpkg: reljar_client reljar_server relzip relsrc

reob:
	cd $(MCP); ./reobfuscate.sh

reljar_client:
	mkdir -p jars
	jar cf jars/$(RELJAR_CLIENT) \
    -C $(MCP)/reobf/minecraft $(MODCLASS_CLIENT).class \
    -C $(MCP)/reobf/minecraft $(MODNAME).class \
    -C $(MCP)/reobf/minecraft $(MODPKG) \
    -C $(MCP)/bin/minecraft $(MODPKG)/resources

reljar_server:
	mkdir -p jars
	jar cf jars/$(RELJAR_SERVER) \
    -C $(MCP)/reobf/minecraft_server $(MODCLASS_SERVER).class \
    -C $(MCP)/reobf/minecraft_server $(MODNAME).class \
    -C $(MCP)/reobf/minecraft_server $(MODPKG)

relzip:
	rm -f $(RELZIP)
	cd jars; zip -r ../$(RELZIP) $(RELJAR_CLIENT) $(RELJAR_SERVER)
	zip -r $(RELZIP) README.html CHANGES.txt doc

relsrc:
	rm -f $(RELSRC)
	zip -r $(RELSRC) $(SRC_INCLUDE)

$(CDEST_CLIENT):
	mkdir -p $(CDEST_CLIENT)

$(CDEST_SERVER):
	mkdir -p $(CDEST_SERVER)
	
upload:
	rsync --progress --times $(RELZIP) $(RELSRC) $(COSC):$(WEBDIR)
	ssh $(COSC) 'cd $(WEBDIR); unzip -o $(RELZIP)'

backup: backup_subdir
	DATE=`date +%Y-%m-%d`; \
	TARFILE=$(BACKUP_DIR)/$(PROJECT)-$$DATE.tar.gz; \
	tar zcvf $$TARFILE $(BACKUP_EXCLUDE) *; \
	ls -l $$TARFILE

backup_subdir:
	cd $(BACKUP_VOL); mkdir -p $(BACKUP_SUBDIR)
