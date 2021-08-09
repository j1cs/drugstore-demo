import { Controller, Get, Logger } from '@nestjs/common';

@Controller()
export class AppController {
  private readonly logger = new Logger(AppController.name);
  @Get()
  getHello(): string {
    this.logger.log('heartbeat (?)');
    return 'OK';
  }
}
