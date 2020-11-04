import { Comment } from '../comments';
export class PostDTO {

    constructor(
        public codigo_id: number,
        public fecha: Date,
        public usuario_id: number,
        public mensaje: string,
        public tipo: string,
        public path_media: string,
        public usuario_nm: string,
        public pulsos: number,
        public globulos: number,
        public desc_fecha: string,
        public pulseado: boolean,
        public globuleado: boolean,
        public comments: Comment[],
        public jefe: boolean,
        public pulsos_detalle: [],
        public destino_id: number,
        public destino_nm: string,
        public etiquetados: []
    ) {}
}
