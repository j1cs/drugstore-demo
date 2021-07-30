import { NotFoundException } from '@nestjs/common';

export class CatNotFoundException extends NotFoundException {
  constructor(catId: number) {
    super(`Cat with id ${catId} not found`);
  }
}
