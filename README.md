# InteractiveAutomaton
An interactive automaton for a domotic system that commands a wireless plug and interact with synthesized voice and automatic Speech recognition. Tested on Raspbian but developed in pure Java. Internally using MaryTTS for Linux. This project requires the [MaryTTSManager](https://github.com/gianpaolocoro/MaryTTSManager), the [WholeWordSpeechRecognizer](https://github.com/gianpaolocoro/WholeWordAutomaticSpeechRecognizer), and the [PlugsController](https://github.com/gianpaolocoro/PlugsController) projects.

## Software
The distribution is made up of a Linux software (Windows is available too if MaryTTS - Windows is installed) with scripts to stop and start the interactive automaton. The **distro** folder contains a compiled version of the software with start and stop scripts.

## Required Software

- [Raspbian](https://downloads.raspberrypi.org/raspbian_latest)

- [Oracle Java 8](https://www.linuxbabe.com/desktop-linux/install-oracle-java-8-debian-jessie-raspbian-jessie-via-ppa)

## Required Hardware

- [Raspberry Pi 3 Kit](https://www.amazon.it/Raspberry-Official-Alimentatore-Ufficiale-Dissipatori/dp/B01D0JDM5W/ref=sr_1_2?s=pc&ie=UTF8&qid=1508703636&sr=1-2&keywords=raspberry+pi+3)
- [USB Microphone](https://www.amazon.it/gp/product/B01CQKCTSM/ref=oh_aui_detailpage_o05_s00?ie=UTF8&psc=1)
- [Speakers](https://www.amazon.it/gp/product/B00JRW0M32/ref=oh_aui_detailpage_o06_s00?ie=UTF8&psc=1)
- [Orvibo Wireless Socket S20](https://www.amazon.it/Socket-ORVIBO-controllo-remoto-Android/dp/B01ID0H7D6)

## Properties File
The process.properties file allows tuning some parameters, like the sensitivity of the microphone and the accuracy of the Speech recognizer:

#### address of the remote plug to control
plugIPAddress=192.168.1.125
#### path to the MaryTTS installation (to change if Windows is to use or the Text-To-Speech engine is installed in another folder)
maryPath=/home/pi/workspace/MaryTTSManager/marytts-5.2
#### voice to use
maryVoice=istc-lucia-hsmm
#### hotword to activate the assistant
hotword=assistente
#### hotword to confirm the activation of the plug
activationHotword=si
#### Speech recognition score thresholds for hotword and activatio word. Increasing towards 0 means more rigid recognition by the automaton, i.e. word should be uttered clearer and clearer
thresholdHotword=-8800
thresholdActivationWord=-7500
#### sensitivity of the microphone (0,100) - higher is less sensitive
sensitivity=50
#### maximum silence (in sec) while expecting a word to be uttered
maxSilence=20
#### maximum silence (in sec) to wait after a word is uttered
maxSilenceAfterWord=-0.3
#### maximum time length (in sec) of an uttered word
maxSpeech=10
#### tolerance of a word: it is allowed to be in the set of the first nBest recognized words
nBest=9
#### path to the Speech recognition models
modelsPath=/home/pi/workspace/WholeWordSpeechRecognizer/MODELS/
#### use bluetooth speakers (experimental)
useBlueToothAudio=false
