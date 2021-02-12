#/bin/sh
set -e
cd $(dirname "$0")

mvn clean install
scp target/*.jar santa@kerstproject-server-01:/var/MineCraftPlugins/
