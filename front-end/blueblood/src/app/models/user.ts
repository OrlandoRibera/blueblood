export class User {
    constructor(
        public id: number,
        public name: string,
        public nick: string,
        public email: string,
        public password: string,
        public area_id: number,
        public position_id: number,
        public city_id: number,
        public birthdate: Date,
        public contractDate: Date,
        public boss_id: number,
        public branch_id: number,
        public moderador: boolean

    ) {}
}
