#!/bin/bash

# Función para instalar Docker
install_docker() {
    sudo apt update
    sudo apt install -y apt-transport-https ca-certificates curl software-properties-common
    curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -
    sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu focal stable"
    sudo apt update
    sudo apt install -y docker-ce
    sudo systemctl status docker
}

# Función para instalar Git
install_git() {
    sudo apt install -y git
}

# Función para configurar Git
config_git() {
    read -p "Ingresa tu nombre de usuario de GitHub: " github_username
    read -p "Ingresa tu correo electrónico de GitHub: " github_email
    git config --global user.name "$github_username"
    git config --global user.email "$github_email"
    git config --global credential.helper store
}

# Preguntar si se desea instalar Docker
read -p "¿Deseas instalar Docker? (y/n): " install_docker_choice
if [[ $install_docker_choice == "y" || $install_docker_choice == "Y" ]]; then
    # Ejecutar la función para instalar Docker
    install_docker
fi

# Preguntar si se desea instalar Git
read -p "¿Deseas instalar Git? (y/n): " install_git_choice
if [[ $install_git_choice == "y" || $install_git_choice == "Y" ]]; then
    # Ejecutar la función para instalar Git
    install_git
fi

# Preguntar si deseas configurar git 
read -p "¿Deseas configurar Git? (y/n): " config_git_choice
if [[ $config_git_choice == "y" || $config_git_choice == "Y" ]]; then
    config_git
fi

