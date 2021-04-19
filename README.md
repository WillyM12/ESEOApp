# ESEOApp
 Projet final Android ESEO S8

    - L'icône de l'application doit être personnalisée. OK
    - Les textes doivent être « i18n » (en Français et en Anglais) OK
    - La structure de votre application doit être rangée en « package », en suivant l'organisation proposé en cours, ou la vôtre si celle-ci montre une organisation compréhensible. OK
    - Un « splashscreen » qui s'affiche au minimum 2s. Celui-ci doit être constitué d'une image « logo », et d'un texte indiquant votre nom + prénom ainsi que l'année. OK
    Une activity Home avec :
        - Un logo OK
        3 éléments cliquables :
            - Localisation OK
            - Historique des localisations précédemment réalisé. OK
            - Paramétrages OK
    Une activity Localisation permettant de :
        - Localiser la personne (via le GPS ou le Réseau au choix). OK
        - Une fois la localisation obtenue, la distance entre l'ESEO et sa position doit être affichée en kilomètre. OK
        - À chaque localisation, à l'aide des SharedPreferences, enregistrez la demande pour la lister dans la vue « historique ». OK
        - Vous devez gérer correctement le flow permission comme vue ensemble (avec gestion des erreurs) OK
    Une activity Historique :
        Utilisation d'un Recyclerview qui doit contenir :
            - Affiche l'historique des positions enregistrées. OK
            - Bonus : Action pour vider la liste. OK
            - Bonus : Si la liste est vide, l'accès à cette activity doit être impossible. OK
            - Bonus : Ouverture de l'application « de cartographie » du téléphone à la position enregistrée. OK
            - Bonus : Affichage pour chaque élément du « reverse de la position GPS en texte » (comme vu ensemble).
    Une activity Paramètres :
        Utilisation d'un Recyclerview qui doit contenir :
            - L'accès aux paramètres de l'application. OK
            - L'accès au paramétrage de localisation du téléphone. OK
            - Ouverture de l'application « carte » du téléphone sur la position géo de l'ESEO. OK
            - Ouverture du site de l'ESEO. OK
            - Ouverture de l'application « email » pour vous contacter. (lien type mailto) OK


Lien vers la vidéo de démonstration :
https://youtu.be/4baNufSt5DM
