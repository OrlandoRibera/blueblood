export class Post {

    constructor(
        public codigo_id: number,
        public fecha: Date,
        public usuario_id: number,
        public mensaje: string,
        public tipo: string,
        public path_media: string,
        public desc_fecha: string
    ) {}
}

