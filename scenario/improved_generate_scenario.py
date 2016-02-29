#!/usr/bin/env python

import numpy as np
import random, codecs

integer_data = np.arange(1, 11)


def generate_userpass():
	i = random.choice(integer_data)
	if i != 10:
		return "user%s pass%s" % (i, i)
	else: 
		return "admin admin"

def generate_userpass_Admin():
	i = random.choice([1,2,3,4,5])
	return "a%s pa%s" % (i, i)



random_set = np.arange(0, 10)
def generate_user():
	i = random.choice(random_set)
	return "user%s" % i


commands_names = [
"exit",
"register_user",
"register_admin",
"show_users",
"sign_in",
"sign_out",
"whoami",
"delete_user",
"rename_user",
"which_group",
"help",
"create_folder",
"pwd",
"show_folders",
"current_folder",
"ls_folders",
"cd",
"add_item",
"show_items",
"add_item_property",
"show_item_properties",
"ls",
"buy",
"put",
"history",
"view_order",
]

def take_cmdname_randomly():
	return random.choice(commands_names)


def generate_foldername():
	i =random.choice([1,2,3,4,5])
	return "folder%s" % i

def generate_item_name():
	i =random.choice([1,2,3,4,5])
	return "item%s" % i

def generate_item_name_with_key_vals():
	i =random.choice([1,2,3,4,5])
	keyval_properties = ""
	for k in range(0, 4):
		keyval_properties += "key%s=val%s " % (random.choice([1,2,3,4,5,6,7,8,9,10]), random.choice([1,2,3,4,5,6,7,8,9,10]))
	return "item%s" % i + " " +keyval_properties


def randomize_cmds():
	cmd_template = [
	"sign_in %s" % generate_userpass_Admin(),
	"sign_in %s" % generate_userpass(),
	"register_user %s" % generate_userpass(),
	"register_admin %s" % generate_userpass(),
	"show_users",
	"whoami",
	"#delete_user %s" % generate_user(),
	"rename_user %s %s" % (generate_user(), generate_user),
	"which_group" ,
	"help %s" % take_cmdname_randomly(),
	"create_folder %s" % generate_foldername(),
	"pwd" ,
	"show_folders" ,
	"current_folder" ,
	"ls_folders",
	"cd %s" % generate_foldername(),
	"add_item %s" % generate_item_name_with_key_vals(),
	"show_items" ,
	"add_item_property %s" % generate_item_name_with_key_vals(),
	"show_item_properties %s" % generate_item_name(),
	"ls" ,
	"buy",
	"put %s" % generate_item_name(),
	"#history",
	"#view_order",
	"sign_out",
	]
	return random.choice(cmd_template)




def make_scenario(filename, N=10000):
	#filename = "scenario_i_%s.script" % (random.choice([0,1,2,3,4,5]))
	with codecs.open(filename, 'w', "utf8") as cf:
		for i in range(N):
			cmd_data = randomize_cmds()
			if not cmd_data.startswith("#"):
			 	cf.write(cmd_data + "\n")

			if i == 1000 or i == 2000 or i == 3000 or i == 5000 or i == 7000:
				cf.write("sign_out" + "\n")

if __name__ == '__main__':
	for i in [0,1,2,3,4,5,6,7,8,9]:
		make_scenario("scenario_i_%s.script" % str(i))
