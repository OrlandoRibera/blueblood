export class Comment {
    constructor(
        public comentario_id: number,
        public usuario_id: number,
        public fecha: Date,
        public comentario: string,
        public usuario_nm: string,
        public post_id: number
    ) {}
}
