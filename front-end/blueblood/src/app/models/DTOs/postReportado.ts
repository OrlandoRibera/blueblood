export class PostReportado {
    constructor(
        public post_id:	number,
        public usuario_id:	number,
        public fecha:	Date,
        public motivo:	string,
        public usuario_nm: string,
        public censurado: boolean,
        public denunciante_nm: string
    ) {}
}
